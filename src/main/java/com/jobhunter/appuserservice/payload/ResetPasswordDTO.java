package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResetPasswordDTO {
    @NotBlank(message = MessageConstants.EMAIL_OR_PHONE_NUMBER_CAN_NOT_BE_EMPTY)
    private String username;
    private String code;
    private UUID codeId;
    @NotBlank(message = MessageConstants.PASSWORD_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = RestConstants.PASSWORD_REGEX, message = MessageConstants.PASSWORD_SHOULD_MATCH_REGEX)
    private String password;
    @NotBlank(message = MessageConstants.CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY)
    private String confirmPassword;
}
