package com.jobhunter.appuserservice.controller;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.*;
import com.jobhunter.appuserservice.security.CurrentUser;
import com.jobhunter.appuserservice.utils.RestConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.UUID;

@RequestMapping(UserController.BASE_PATH)
public interface UserController {
    String BASE_PATH = RestConstants.BASE_PATH_V1 + "/account";
    String BLOCK = "/block";
    String UNBLOCK = "/unblock";
    String LIST = "/list";
    String EMAIL_UPDATE = "/email";
    String VERIFY_EMAIL = "/verify-email";
    String PHONE_NUMBER_UPDATE = "/phone";
    String VERIFY_PHONE_NUMBER = "/verify-phone";
    String PASSWORD_UPDATE = "/password";
    String ADDRESS_UPDATE = "/address";
    String AVATAR_UPDATE = "/avatar";

    @GetMapping
    Response<UserWithoutPasswordDTO> getMe(@CurrentUser User user);

    @PutMapping(BLOCK + "/{userId}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    Response<String> blockUser(@PathVariable UUID userId);

    @PutMapping(UNBLOCK + "/{userId}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    Response<String> unblockUser(@PathVariable UUID userId);

    @GetMapping(LIST)
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    Response<PaginationDTO<?>> list(@RequestParam(defaultValue = RestConstants.APPLICANT) String userRole,
                                    @RequestParam(required = false) boolean asc,
                                    @RequestParam(defaultValue = "createdAt") String sortType,
                                    @RequestParam(defaultValue = "") String search,
                                    @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE) int page,
                                    @RequestParam(defaultValue = RestConstants.DEFAULT_PAGE_SIZE) int size);

    @PutMapping
    Response<UserWithoutPasswordDTO> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO, @CurrentUser User currentUser);

    @PutMapping(ADDRESS_UPDATE)
    Response<String> updateAddress(@CurrentUser User user, @Valid @RequestBody AddressUpdateDTO address);

    @GetMapping(ADDRESS_UPDATE)
    Response<List<RegionDTO>> getRegions();

    @PutMapping(AVATAR_UPDATE)
    Response<String> updateAvatar(@CurrentUser User currentUser, MultipartHttpServletRequest request);

    @PutMapping(EMAIL_UPDATE)
    Response<CodeDTO> updateEmail(@CurrentUser User currentUser, @Valid @RequestBody EmailUpdateDTO email);

    @PutMapping(VERIFY_EMAIL)
    Response<String> verifyEmail(@CurrentUser User currentUser, @Valid @RequestBody VerifyDTO verifyDTO);

    @PutMapping(PHONE_NUMBER_UPDATE)
    Response<CodeDTO> updatePhone(@CurrentUser User currentUser, @Valid @RequestBody PhoneUpdateDTO phone);

    @PutMapping(VERIFY_PHONE_NUMBER)
    Response<String> verifyPhoneNumber(@CurrentUser User currentUser, @Valid @RequestBody VerifyDTO verifyDTO);

    @PutMapping(PASSWORD_UPDATE)
    Response<String> updatePassword(@CurrentUser User currentUser, @Valid @RequestBody UpdatePasswordDTO passwordDTO);

    @DeleteMapping
    @PreAuthorize(value = "hasAnyAuthority('APPLICANT', 'COMPANY')")
    Response<String> deleteUser(@CurrentUser User currentUser);
}
