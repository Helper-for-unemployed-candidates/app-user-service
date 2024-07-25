package com.jobhunter.appuserservice.service;

import com.jobhunter.appuserservice.entities.Applicant;
import com.jobhunter.appuserservice.entities.Company;
import com.jobhunter.appuserservice.entities.Sphere;
import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.enums.RoleEnum;
import com.jobhunter.appuserservice.exceptions.RestException;
import com.jobhunter.appuserservice.mapper.ApplicantMapper;
import com.jobhunter.appuserservice.mapper.CompanyMapper;
import com.jobhunter.appuserservice.mapper.UserMapper;
import com.jobhunter.appuserservice.payload.*;
import com.jobhunter.appuserservice.repository.*;
import com.jobhunter.appuserservice.repository.projection.SphereProjection;
import com.jobhunter.appuserservice.security.JwtTokenProvider;
import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final SmsService smsService;
    private final SphereRepository sphereRepository;
    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;
    private final ApplicantMapper applicantMapper;
    private final ApplicantRepository applicantRepository;

    public AuthServiceImpl(JwtTokenProvider jwtTokenProvider, @Lazy AuthenticationManager authenticationManager, UserRepository userRepository, UserMapper userMapper, @Lazy PasswordEncoder passwordEncoder, EmailService emailService, SmsService smsService, SphereRepository sphereRepository, CompanyMapper companyMapper, CompanyRepository companyRepository, ApplicantMapper applicantMapper, ApplicantRepository applicantRepository, SmsCodeRepository smsCodeRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.smsService = smsService;
        this.sphereRepository = sphereRepository;
        this.companyMapper = companyMapper;
        this.companyRepository = companyRepository;
        this.applicantMapper = applicantMapper;
        this.applicantRepository = applicantRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByEmailIgnoreCaseOrPhoneIgnoreCase(username, username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> RestException.restThrow(MessageConstants.USER_NOT_FOUND_WITH_ID + id));
    }

    @Override
    @Transactional
    public Response<CodeDTO> signUp(SignUpDTO signUpDTO) {
        if (Objects.isNull(signUpDTO.getPhone()) && Objects.isNull(signUpDTO.getEmail()))
            throw RestException.restThrow(MessageConstants.PHONE_NUMBER_AND_EMAIL_BOTH_CAN_NOT_BE_NULL);

        if (!Objects.equals(signUpDTO.getPassword(), signUpDTO.getConfirmPassword()))
            throw RestException.restThrow(MessageConstants.PASSWORDS_AND_PRE_PASSWORD_NOT_EQUAL);

        User user = checkIfUserAlreadyRegistered(signUpDTO);

        RoleEnum role = signUpDTO.getRole();
        if (Objects.equals(role, RoleEnum.APPLICANT)) return createApplicant(signUpDTO, user);
        else if (Objects.equals(role, RoleEnum.COMPANY)) return createCompany(signUpDTO, user);
        throw RestException.restThrow(MessageConstants.USER_HAS_TO_CHOOSE_A_ROLE);
    }

    private User checkIfUserAlreadyRegistered(SignUpDTO signUpDTO) {
        User user;
        Optional<User> tempUser;
        if (Objects.nonNull(signUpDTO.getPhone())) {
            smsService.checkIfThereAreTooManyRequestsOrThrow(signUpDTO.getPhone());
            tempUser = userRepository.findByPhoneIgnoreCase(signUpDTO.getPhone());
        } else {
            emailService.checkIfThereAreTooManyRequestsOrThrow(signUpDTO.getEmail());
            tempUser = userRepository.findByEmailIgnoreCase(signUpDTO.getEmail());
        }

        if (tempUser.isPresent() && tempUser.get().isEnabled())
            throw RestException.restThrow(MessageConstants.PHONE_OR_EMAIL_ALREADY_REGISTERED);

        if (tempUser.isPresent()) {
            userMapper.updateUserForSignUp(tempUser.get(), signUpDTO);
            user = tempUser.get();
        } else user = userMapper.toUser(signUpDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);
        return user;
    }

    private Response<CodeDTO> createCompany(SignUpDTO signUpDTO, User user) {
        Company company;
        CompanyCreateDTO companyDTO = signUpDTO.getCompany();

        if (companyDTO == null)
            throw RestException.restThrow(MessageConstants.COMPANY_CAN_NOT_BE_NULL);

        if (Objects.isNull(companyDTO.getCompanyName()))
            throw RestException.restThrow(MessageConstants.COMPANY_NAME_CAN_NOT_BE_BLANK);

        if (Objects.isNull(companyDTO.getCompanyLicense()))
            throw RestException.restThrow(MessageConstants.COMPANY_LICENSE_CAN_NOT_BE_EMPTY);

        if (Objects.isNull(companyDTO.getCompanySphereId()))
            throw RestException.restThrow(MessageConstants.COMPANY_SPHERE_SHOULD_BE_CHOSEN);

        Sphere sphere = sphereRepository.findById(companyDTO.getCompanySphereId()).orElseThrow(() -> RestException.restThrow(MessageConstants.INVALID_SPHERE_ID));

        if (user.getId() != null) {
            company = companyRepository.findByUserId(user.getId()).orElseThrow(() -> RestException.restThrow(MessageConstants.COMPANY_NOT_FOUND));
            companyMapper.updateCompanyForSignUp(company, companyDTO);
        } else {
            company = companyMapper.toCompany(companyDTO);
            company.setUser(user);
        }
        company.setCompanySphere(sphere);
        companyRepository.save(company);
        return sendVerificationCode(user, false);
    }

    private Response<CodeDTO> createApplicant(SignUpDTO signUpDTO, User user) {
        Applicant applicant;
        ApplicantCreateDTO applicantDTO = signUpDTO.getApplicant();

        if (Objects.isNull(applicantDTO))
            throw RestException.restThrow(MessageConstants.APPLICANT_CAN_NOT_BE_NULL);

        if (Objects.isNull(applicantDTO.getFirstName()))
            throw RestException.restThrow(MessageConstants.FIRST_NAME_CAN_NOT_BE_EMPTY);

        if (Objects.isNull(applicantDTO.getLastName()))
            throw RestException.restThrow(MessageConstants.LAST_NAME_CAN_NOT_BE_EMPTY);

        if (user.getId() != null) {
            applicant = applicantRepository.findByUserId(user.getId()).orElseThrow(() -> RestException.restThrow(MessageConstants.APPLICANT_NOT_FOUND));
            applicantMapper.updateApplicantForSignUp(applicant, applicantDTO);
        } else {
            applicant = applicantMapper.toApplicant(applicantDTO);
            applicant.setUser(user);
        }

        applicantRepository.save(applicant);
        return sendVerificationCode(user, false);
    }

    private Response<CodeDTO> sendVerificationCode(User user, boolean check) {
        if (Objects.nonNull(user.getPhone())) {
            CodeDTO smsCodeDTO = smsService.sendVerificationSms(user, check);
            return Response.successResponse(smsCodeDTO, MessageConstants.SMS_HAS_BEEN_SENT_TO_YOUR_PHONE_NUMBER);
        }
        CodeDTO emailCodeDTO = emailService.sendVerificationEmail(user, check);
        return Response.successResponse(emailCodeDTO, MessageConstants.VERIFICATION_CODE_SENT_TO_EMAIL);
    }

    @Override
    public Response<TokenDTO> signIn(SignInDTO signInDTO) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDTO.getUsername().toLowerCase(), signInDTO.getPassword()));
        } catch (DisabledException | LockedException | AccountExpiredException | CredentialsExpiredException e) {
            throw RestException.restThrow(e.getMessage(), RestConstants.USER_NOT_ACTIVE, HttpStatus.UNAUTHORIZED);
        } catch (BadCredentialsException | UsernameNotFoundException badCredentialsException) {
            throw RestException.restThrow(MessageConstants.LOGIN_OR_PASSWORD_ERROR, RestConstants.INCORRECT_USERNAME_OR_PASSWORD, HttpStatus.UNAUTHORIZED);
        }

        User principal = (User) authentication.getPrincipal();
        TokenDTO tokenDTO = makeTokenDTO(principal);

        return Response.successResponse(tokenDTO);
    }

    @Override
    public Response<CodeDTO> resendVerificationCode(String username) {
        User first = userRepository.findFirstByEmailIgnoreCaseOrPhoneIgnoreCaseAndEnabledFalse(username, username)
                .orElseThrow(() -> RestException.restThrow(MessageConstants.INVALID_PHONE_NUMBER_OR_EMAIL));
        return sendVerificationCode(first, true);
    }

    @Override
    public Response<String> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        if (!Objects.equals(resetPasswordDTO.getPassword(), resetPasswordDTO.getConfirmPassword()))
            throw RestException.restThrow(MessageConstants.PASSWORDS_AND_PRE_PASSWORD_NOT_EQUAL);

        User user = verify(VerifyDTO.builder()
                .email(resetPasswordDTO.getUsername())
                .phoneNumber(resetPasswordDTO.getUsername())
                .code(resetPasswordDTO.getCode())
                .codeId(resetPasswordDTO.getCodeId())
                .build());

        if (!user.isEnabled())
            throw RestException.restThrow(MessageConstants.USER_NOT_FOUND);

        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        userRepository.save(user);
        return Response.successResponse(MessageConstants.PASSWORD_SUCCESSFULLY_CHANGED);
    }

    @Override
    public Response<CodeDTO> forgotPassword(String username) {
        User user = userRepository.findFirstByEmailIgnoreCaseOrPhoneIgnoreCase(username, username)
                .orElseThrow(() -> RestException.restThrow(MessageConstants.INVALID_PHONE_NUMBER_OR_EMAIL));
        if (!user.isEnabled())
            throw RestException.restThrow(MessageConstants.USER_NOT_FOUND);
        return sendVerificationCode(user, true);
    }

    @Override
    public Response<String> verifyAccount(VerifyDTO verifyDTO) {
        User user = verify(verifyDTO);
        if (user.isEnabled())
            throw RestException.restThrow(MessageConstants.USER_ALREADY_VERIFIED);

        user.setEnabled(true);
        userRepository.save(user);

        return Response.successResponse(MessageConstants.VERIFIED);
    }

    private User verify(VerifyDTO verifyDTO) {
        if (verifyDTO.getPhoneNumber() != null) {
            smsService.checkIfVerificationCodeIsValidOrThrow(verifyDTO);
            return userRepository.findByPhoneIgnoreCase(verifyDTO.getPhoneNumber())
                    .orElseThrow(() -> RestException.restThrow(MessageConstants.INVALID_PHONE_NUMBER));

        } else if (verifyDTO.getEmail() != null) {
            emailService.checkIfVerificationCodeIsValidOrThrow(verifyDTO);
            return userRepository.findByEmailIgnoreCase(verifyDTO.getEmail())
                    .orElseThrow(() -> RestException.restThrow(MessageConstants.INVALID_EMAIL));
        } else throw RestException.restThrow(MessageConstants.EMAIL_OR_PHONE_NUMBER_CAN_NOT_BE_EMPTY);
    }

    @Override
    public Response<List<SphereProjection>> initialData() {
        return Response.successResponse(sphereRepository.list());
    }

    /*
    @Override
    public Response<String> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        if (!Objects.equals(resetPasswordDTO.getPassword(), resetPasswordDTO.getPrePassword()))
            throw RestException.restThrow(MessageConstants.PASSWORDS_AND_PRE_PASSWORD_NOT_EQUAL);

        User user = userRepository.findByEmailAndEnabledTrue(resetPasswordDTO.getEmail().toLowerCase()).orElseThrow(() -> RestException.restThrow(MessageConstants.USER_NOT_FOUND));

        if (!Objects.equals(user.getVerificationCode(), resetPasswordDTO.getVerificationCode()))
            throw RestException.restThrow(MessageConstants.VERIFICATION_CODE_INCORRECT_OR_USED);

        user.setVerificationCode(null);
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        userRepository.save(user);

        return Response.successResponseForMsg(MessageConstants.PASSWORD_SUCCESSFULLY_CHANGED);
    }

    @Override
    public Response<String> forgotPassword(String email) {
        User user = userRepository.findByEmailAndEnabledTrue(email.toLowerCase()).orElseThrow(() -> RestException.restThrow(MessageConstants.USER_NOT_FOUND));
        String verificationCode = UUID.randomUUID().toString();
        user.setVerificationCode(verificationCode);
        userRepository.save(user);

        String url = frontHostname + frontResetPasswordUrl + "/" + email + "/" + verificationCode;

        emailService.passwordResetEmail(user, url);

        return Response.successResponseForMsg(MessageConstants.LINK_SENT_FOR_FORGOT_PASSWORD);
    }
*/

    private TokenDTO makeTokenDTO(User user) {
        Timestamp tokenIssuedAt = new Timestamp(System.currentTimeMillis() / 1000 * 1000);
        String accessToken = jwtTokenProvider.generateAccessToken(user, tokenIssuedAt);

//        user.setTokenIssuedAt(tokenIssuedAt);
//        userRepository.save(user);

        return TokenDTO.builder().accessToken(accessToken).build();
    }
}
