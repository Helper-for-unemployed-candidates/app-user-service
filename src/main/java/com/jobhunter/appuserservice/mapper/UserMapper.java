package com.jobhunter.appuserservice.mapper;

import com.jobhunter.appuserservice.entities.User;
import com.jobhunter.appuserservice.payload.SignUpDTO;
import com.jobhunter.appuserservice.payload.UserWithoutPasswordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = AddressMapper.class
)
public interface UserMapper {

    @Mapping(target = "avatarId", expression = "java( user.getAvatar().getId() )")
    UserWithoutPasswordDTO toUserWithoutPasswordDTO(User user);

    User toUser(SignUpDTO signUpDTO);

    @IgnoreId
    void updateUserForSignUp(@MappingTarget User user, SignUpDTO signUpDTO);
}
