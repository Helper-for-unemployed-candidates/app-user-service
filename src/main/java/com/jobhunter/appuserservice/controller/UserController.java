package com.jobhunter.appuserservice.controller;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.Response;
import com.jobhunter.appuserservice.payload.UserWithoutPasswordDTO;
import com.jobhunter.appuserservice.security.CurrentUser;
import com.jobhunter.appuserservice.utils.RestConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(UserController.BASE_PATH)
public interface UserController {
    String BASE_PATH = RestConstants.BASE_PATH_V1 + "/account";

    @GetMapping
    Response<UserWithoutPasswordDTO> getMe(@CurrentUser User user);
}
