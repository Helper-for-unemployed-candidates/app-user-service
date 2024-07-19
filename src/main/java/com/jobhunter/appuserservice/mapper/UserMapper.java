package com.jobhunter.appuserservice.mapper;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.SignUpDTO;
import com.jobhunter.appuserservice.payload.UserWithoutPasswordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserMapper {
    UserWithoutPasswordDTO toUserWithoutPasswordDTO(User user);

    User toUser(SignUpDTO signUpDTO);

    @Mapping(target = "id", ignore = true)
    void updateUserForSignUp(@MappingTarget User user, SignUpDTO signUpDTO);
}
