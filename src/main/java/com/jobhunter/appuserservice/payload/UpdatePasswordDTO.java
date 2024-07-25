package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {
    @NotBlank(message = MessageConstants.OLD_PASSWORD_CAN_NOT_BE_EMPTY)
    private String oldPassword;
    @NotBlank(message = MessageConstants.PASSWORD_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = RestConstants.PASSWORD_REGEX, message = MessageConstants.PASSWORD_SHOULD_MATCH_REGEX)
    private String password;
    @NotBlank(message = MessageConstants.CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY)
    private String confirmPassword;
}
