package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.enums.Role;
import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO {
    @NotNull(message = MessageConstants.ROLE_SHOULD_BE_CHOSEN)
    private Role role;

    private ApplicantCreateDTO applicant;

    private CompanyCreateDTO company;

    @Pattern(regexp = RestConstants.EMAIL_REGEX, message = MessageConstants.INVALID_EMAIL)
    private String email;

    @Pattern(regexp = RestConstants.UZB_PHONE_NUMBER_REGEX, message = MessageConstants.INVALID_PHONE_NUMBER)
    private String phone;

    @NotBlank(message = MessageConstants.PASSWORD_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = RestConstants.PASSWORD_REGEX, message = MessageConstants.PASSWORD_SHOULD_MATCH_REGEX)
    private String password;

    @NotBlank(message = MessageConstants.CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY)
    private String confirmPassword;
}
