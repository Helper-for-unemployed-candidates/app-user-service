package com.jobhunter.appuserservice.service;

import com.jobhunter.appuserservice.entities.*;
import com.jobhunter.appuserservice.enums.Region;
import com.jobhunter.appuserservice.enums.RoleEnum;
import com.jobhunter.appuserservice.exceptions.RestException;
import com.jobhunter.appuserservice.mapper.ApplicantMapper;
import com.jobhunter.appuserservice.mapper.CityMapper;
import com.jobhunter.appuserservice.mapper.CompanyMapper;
import com.jobhunter.appuserservice.mapper.UserMapper;
import com.jobhunter.appuserservice.payload.*;
import com.jobhunter.appuserservice.repository.*;
import com.jobhunter.appuserservice.repository.projection.ApplicantProjection;
import com.jobhunter.appuserservice.repository.projection.CompanyProjection;
import com.jobhunter.appuserservice.utils.CommonUtils;
import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CityMapper cityMapper;
    private final UserRepository userRepository;
    private final ApplicantRepository applicantRepository;
    private final CompanyRepository companyRepository;
    private final UserMapper userMapper;
    private final ApplicantMapper applicantMapper;
    private final CompanyMapper companyMapper;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentService attachmentService;
    private final SphereRepository sphereRepository;
    private final CityRepository cityRepository;
    private final AddressRepository addressRepository;
    private final EmailService emailService;
    private final SmsService smsService;

    @Override
    public Response<UserWithoutPasswordDTO> getMe(User currentUser) {
        UserWithoutPasswordDTO userWithoutPasswordDTO = userMapper.toUserWithoutPasswordDTO(currentUser);
        if (Objects.equals(currentUser.getRole(), RoleEnum.APPLICANT)) {
            Applicant applicant = getApplicant(currentUser);
            userWithoutPasswordDTO.setApplicant(applicantMapper.toApplicantDTO(applicant));
        } else if (Objects.equals(currentUser.getRole(), RoleEnum.COMPANY)) {
            Company company = getCompany(currentUser);
            userWithoutPasswordDTO.setCompany(companyMapper.toCompanyDTO(company));
        }
        return Response.successResponse(userWithoutPasswordDTO);
    }

    @Override
    public Response<String> blockUser(UUID userId) {
        userRepository.markAsBlocked(userId);
        return Response.successResponse(MessageConstants.SUCCESS);
    }

    @Override
    public Response<String> unblockUser(UUID userId) {
        userRepository.markAsUnblocked(userId);
        return Response.successResponse(MessageConstants.SUCCESS);
    }

    @Override
    public Response<PaginationDTO<?>> list(String userRole, boolean asc, String sortType, String search, int page, int size) {
        CommonUtils.checkPageAndSize(page, size);
        if (sortType == null)
            throw RestException.restThrow(MessageConstants.SORT_TYPE_CAN_NOT_BE_NULL);

        if (userRole == null)
            throw RestException.restThrow(MessageConstants.USER_ROLE_CAN_NOT_BE_NULL);

        PageRequest request = PageRequest.of(page, size, asc ? Sort.Direction.ASC : Sort.Direction.DESC, sortType);

        if (Objects.equals(userRole, RestConstants.APPLICANT)) {
            Page<ApplicantProjection> list = applicantRepository.list(request);
            return Response.successResponse(
                    PaginationDTO.makeForPage(list.getTotalPages(), page, size, list.getTotalElements(), list.getContent())
            );
        } else if (Objects.equals(userRole, RestConstants.COMPANY)) {
            Page<CompanyProjection> list = companyRepository.list(request);
            return Response.successResponse(
                    PaginationDTO.makeForPage(list.getTotalPages(), page, size, list.getTotalElements(), list.getContent())
            );
        } else throw RestException.restThrow(MessageConstants.INVALID_USER_ROLE);
    }

    @Override
    public Response<UserWithoutPasswordDTO> updateUser(UserUpdateDTO userUpdateDTO, User currentUser) {
        UserWithoutPasswordDTO userWithoutPasswordDTO = userMapper.toUserWithoutPasswordDTO(currentUser);
        if (Objects.nonNull(userUpdateDTO.getApplicant()) && Objects.equals(currentUser.getRole(), RoleEnum.APPLICANT)) {

            Applicant applicant = getApplicant(currentUser);
            applicantMapper.update(applicant, userUpdateDTO.getApplicant());
            applicant = applicantRepository.saveAndFlush(applicant);
            userWithoutPasswordDTO.setApplicant(applicantMapper.toApplicantDTO(applicant));

        } else if (Objects.nonNull(userUpdateDTO.getCompany()) && Objects.equals(currentUser.getRole(), RoleEnum.COMPANY)) {

            Company company = getCompany(currentUser);
            companyMapper.updateCompany(company, userUpdateDTO.getCompany());

            UUID uuid = userUpdateDTO.getCompany().getCompanySphereId();
            if (uuid != null) {
                Sphere sphere = sphereRepository.findById(uuid)
                        .orElseThrow(() -> RestException.restThrow(MessageConstants.INVALID_SPHERE_ID));
                company.setCompanySphere(sphere);
            }

            company = companyRepository.saveAndFlush(company);
            userWithoutPasswordDTO.setCompany(companyMapper.toCompanyDTO(company));
        } else throw RestException.restThrow(MessageConstants.UPDATE_DATA_CAN_NOT_BE_NULL);

        return Response.successResponse(userWithoutPasswordDTO);
    }

    @Override
    public Response<String> updateAddress(User currentUser, AddressUpdateDTO addressUpdateDTO) {
        City city = cityRepository.findById(addressUpdateDTO.getCityId())
                .orElseThrow(() -> RestException.restThrow(MessageConstants.CITY_NOT_FOUND));
        Address address = currentUser.getAddress();
        if (address == null)
            address = new Address();
        address.setCity(city);
        address.setFullAddress(addressUpdateDTO.getFullAddress());
        addressRepository.saveAndFlush(address);

        return Response.successResponse(MessageConstants.SUCCESS);
    }

    @Override
    public Response<List<RegionDTO>> getRegions() {
        Map<Region, List<City>> regions = cityRepository.findAll()
                .stream().collect(Collectors.groupingBy(City::getRegion));
        return Response.successResponse(
                regions.keySet().stream()
                        .map(region -> {
                            List<CityDTO> cities = cityMapper.toCityDTOs(regions.get(region));
                            return RegionDTO
                                    .builder()
                                    .name(region.name())
                                    .cities(cities).
                                    build();
                        })
                        .toList()
        );
    }

    @Override
    public Response<String> updateAvatar(User currentUser, MultipartHttpServletRequest request) {
        Attachment attachment = attachmentService.uploadAttachment(request);
        currentUser.setAvatar(attachment);
        userRepository.save(currentUser);
        return Response.successResponse(MessageConstants.SUCCESS);
    }

    @Override
    public Response<CodeDTO> updateEmail(User currentUser, EmailUpdateDTO email) {
        if (Objects.equals(currentUser.getEmail(), email.getEmail()))
            throw RestException.restThrow(MessageConstants.EMAIL_HAS_NOT_BEEN_CHANGED);

        if (userRepository.existsByEmailIgnoreCaseAndEnabledTrueAndIdNot(email.getEmail(), currentUser.getId()))
            throw RestException.restThrow(MessageConstants.EMAIL_ALREADY_REGISTERED);

        CodeDTO codeDTO = emailService.sendUpdateEmail(email.getEmail(), true);
        return Response.successResponse(codeDTO);
    }

    @Override
    public Response<String> verifyEmail(User currentUser, VerifyDTO verifyDTO) {
        emailService.checkIfVerificationCodeIsValidOrThrow(verifyDTO);

        currentUser.setEmail(verifyDTO.getEmail());
        userRepository.save(currentUser);
        return Response.successResponse(MessageConstants.SUCCESS);
    }

    @Override
    public Response<CodeDTO> updatePhone(User currentUser, PhoneUpdateDTO phone) {
        if (Objects.equals(currentUser.getEmail(), phone.getPhoneNumber()))
            throw RestException.restThrow(MessageConstants.PHONE_NUMBER_HAS_NOT_BEEN_CHANGED);

        if (userRepository.existsByPhoneIgnoreCaseAndEnabledTrueAndIdNot(phone.getPhoneNumber(), currentUser.getId()))
            throw RestException.restThrow(MessageConstants.PHONE_ALREADY_REGISTERED);

        CodeDTO codeDTO = smsService.sendUpdatePhoneSms(phone.getPhoneNumber());
        return Response.successResponse(codeDTO);
    }

    @Override
    public Response<String> verifyPhoneNumber(User currentUser, VerifyDTO verifyDTO) {
        smsService.checkIfVerificationCodeIsValidOrThrow(verifyDTO);

        currentUser.setPhone(verifyDTO.getPhoneNumber());
        userRepository.save(currentUser);
        return Response.successResponse(MessageConstants.SUCCESS);
    }

    @Override
    public Response<String> updatePassword(User currentUser, UpdatePasswordDTO passwordDTO) {
        if (!passwordEncoder.matches(currentUser.getPassword(), passwordDTO.getOldPassword()))
            throw RestException.restThrow(MessageConstants.INVALID_PASSWORD);
        if (!Objects.equals(passwordDTO.getPassword(), passwordDTO.getConfirmPassword()))
            throw RestException.restThrow(MessageConstants.PASSWORDS_AND_PRE_PASSWORD_NOT_EQUAL);
        currentUser.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        userRepository.save(currentUser);
        return Response.successResponse(MessageConstants.SUCCESS);
    }

    @Override
    @Transactional
    public Response<String> deleteUser(User currentUser) {
        userRepository.markAsDeleted(currentUser.getId());
        if (Objects.equals(currentUser.getRole(), RoleEnum.APPLICANT))
            applicantRepository.markAsDeleted(currentUser.getId());
        else companyRepository.markAsDeleted(currentUser.getId());
        return Response.successResponse(MessageConstants.SUCCESS);
    }

    private Company getCompany(User currentUser) {
        return companyRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> {
                    log.error("{} is not company", currentUser.getId());
                    return RestException.restThrow(MessageConstants.COMPANY_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    private Applicant getApplicant(User currentUser) {
        return applicantRepository.findByUserId(currentUser.getId()).orElseThrow(() -> {
            log.error("{} is not applicant", currentUser.getId());
            return RestException.restThrow(MessageConstants.APPLICANT_NOT_FOUND, HttpStatus.INTERNAL_SERVER_ERROR);
        });
    }
}
