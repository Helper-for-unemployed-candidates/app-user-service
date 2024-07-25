package com.jobhunter.appuserservice.controller;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.*;
import com.jobhunter.appuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public Response<UserWithoutPasswordDTO> getMe(User currentUser) {
        return userService.getMe(currentUser);
    }

    @Override
    public Response<String> blockUser(UUID userId) {
        return userService.blockUser(userId);
    }

    @Override
    public Response<String> unblockUser(UUID userId) {
        return userService.unblockUser(userId);
    }

    @Override
    public Response<PaginationDTO<?>> list(String userRole, boolean asc, String sortType, String search, int page, int size) {
        return userService.list(userRole, asc, sortType, search, page, size);
    }

    @Override
    public Response<UserWithoutPasswordDTO> updateUser(UserUpdateDTO userUpdateDTO, User currentUser) {
        return userService.updateUser(userUpdateDTO, currentUser);
    }

    @Override
    public Response<String> updateAddress(User currentUser, AddressUpdateDTO address) {
        return userService.updateAddress(currentUser, address);
    }

    @Override
    public Response<List<RegionDTO>> getRegions() {
        return userService.getRegions();
    }

    @Override
    public Response<String> updateAvatar(User currentUser, MultipartHttpServletRequest request) {
        return userService.updateAvatar(currentUser, request);
    }

    @Override
    public Response<CodeDTO> updateEmail(User currentUser, EmailUpdateDTO email) {
        return userService.updateEmail(currentUser, email);
    }

    @Override
    public Response<String> verifyEmail(User currentUser, VerifyDTO verifyDTO) {
        return userService.verifyEmail(currentUser, verifyDTO);
    }

    @Override
    public Response<CodeDTO> updatePhone(User currentUser, PhoneUpdateDTO phone) {
        return userService.updatePhone(currentUser, phone);
    }

    @Override
    public Response<String> verifyPhoneNumber(User currentUser, VerifyDTO verifyDTO) {
        return userService.verifyPhoneNumber(currentUser, verifyDTO);
    }

    @Override
    public Response<String> updatePassword(User currentUser, UpdatePasswordDTO passwordDTO) {
        return userService.updatePassword(currentUser, passwordDTO);
    }

    @Override
    public Response<String> deleteUser(User currentUser) {
        return userService.deleteUser(currentUser);
    }
}
