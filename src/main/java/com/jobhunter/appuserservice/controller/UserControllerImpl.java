package com.jobhunter.appuserservice.controller;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.Response;
import com.jobhunter.appuserservice.payload.UserWithoutPasswordDTO;
import com.jobhunter.appuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;
    @Override
    public Response<UserWithoutPasswordDTO> getMe(User user) {
        return userService.getMe(user);
    }
}
