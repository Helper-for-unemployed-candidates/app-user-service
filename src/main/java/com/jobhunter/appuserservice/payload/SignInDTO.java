package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInDTO {
    @NotBlank(message = MessageConstants.EMAIL_OR_PHONE_NUMBER_CAN_NOT_BE_EMPTY)
    private String username;

    @NotBlank(message = MessageConstants.PASSWORD_CAN_NOT_BE_EMPTY)
    private String password;
}
