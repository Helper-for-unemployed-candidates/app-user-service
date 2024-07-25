package com.jobhunter.appuserservice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jobhunter.appuserservice.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWithoutPasswordDTO {
    UUID id;
    String phone;
    String email;
    UUID avatarId;
    AddressDTO address;
    RoleEnum role;
    ApplicantDTO applicant;
    CompanyDTO company;
}
