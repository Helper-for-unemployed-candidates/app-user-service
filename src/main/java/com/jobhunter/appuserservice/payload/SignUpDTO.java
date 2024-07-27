package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.enums.RoleEnum;
import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO {
    @Schema(description = "User's role. (APPLICANT or COMPANY)", example = "APPLICANT")
    @NotNull(message = MessageConstants.ROLE_SHOULD_BE_CHOSEN)
    private RoleEnum role;

    @Schema(description = "If role is APPLICANT, then this field is required")
    private ApplicantCreateDTO applicant;

    @Schema(description = "If role is COMPANY, then this field is required")
    private CompanyCreateDTO company;

    @Schema(description = "User's email or phone number", example = "johndoe@example.com or 931234567")
    @NotBlank(message = MessageConstants.EMAIL_OR_PHONE_NUMBER_CAN_NOT_BE_EMPTY)
    private String username;

    /*@Schema(description = "User's email", example = "johndoe@example.com")
    @Pattern(regexp = RestConstants.EMAIL_REGEX, message = MessageConstants.INVALID_EMAIL)
    private String email;

    @Schema(description = "User's phone number", example = "931234567")
    @Pattern(regexp = RestConstants.UZB_PHONE_NUMBER_REGEX, message = MessageConstants.INVALID_PHONE_NUMBER)
    private String phone;*/

    @Schema(description = "User's password. Has to contain one upper and one lower case, a number, a symbol. Minimum length is 8 characters")
    @NotBlank(message = MessageConstants.PASSWORD_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = RestConstants.PASSWORD_REGEX, message = MessageConstants.PASSWORD_SHOULD_MATCH_REGEX)
    private String password;

    @Schema(description = "Has to match password to validate if user didn't mismatched a symbol")
    @NotBlank(message = MessageConstants.CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY)
    private String confirmPassword;
}
