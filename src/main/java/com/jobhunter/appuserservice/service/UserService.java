package com.jobhunter.appuserservice.service;


import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.Response;
import com.jobhunter.appuserservice.payload.UserWithoutPasswordDTO;

public interface UserService {
    Response<UserWithoutPasswordDTO> getMe(User user);
}
