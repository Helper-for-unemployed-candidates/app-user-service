package com.jobhunter.appuserservice.service;


import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    Response<UserWithoutPasswordDTO> getMe(User currentUser);

    Response<String> blockUser(UUID userId);

    Response<String> unblockUser(UUID userId);

    Response<PaginationDTO<?>> list(String userRole, boolean asc, String sortType, String search, int page, int size);

    Response<UserWithoutPasswordDTO> updateUser(UserUpdateDTO userUpdateDTO, User currentUser);

    Response<String> updateAddress(User currentUser, AddressUpdateDTO address);

    Response<List<RegionDTO>> getRegions();

    Response<String> updateAvatar(User currentUser, MultipartHttpServletRequest request);

    Response<CodeDTO> updateEmail(User currentUser, EmailUpdateDTO email);

    Response<String> verifyEmail(User currentUser, VerifyDTO verifyDTO);

    Response<CodeDTO> updatePhone(User currentUser, PhoneUpdateDTO phone);

    Response<String> verifyPhoneNumber(User currentUser, VerifyDTO verifyDTO);

    Response<String> updatePassword(User currentUser, UpdatePasswordDTO passwordDTO);

    Response<String> deleteUser(User currentUser);
}
