package com.jobhunter.appuserservice.service;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.mapper.UserMapper;
import com.jobhunter.appuserservice.payload.Response;
import com.jobhunter.appuserservice.payload.UserWithoutPasswordDTO;
import com.jobhunter.appuserservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Response<UserWithoutPasswordDTO> getMe(User user) {
        UserWithoutPasswordDTO userWithoutPasswordDTO = userMapper.toUserWithoutPasswordDTO(user);
        return Response.successResponse(userWithoutPasswordDTO);
    }
}
