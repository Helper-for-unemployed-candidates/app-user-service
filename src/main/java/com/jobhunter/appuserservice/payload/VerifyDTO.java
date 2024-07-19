package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class VerifyDTO {
    @NotBlank(message = MessageConstants.VERIFICATION_CODE_ID_CAN_NOT_BE_NULL)
    private UUID codeId;
    private String email;
    private String phoneNumber;
    @NotBlank(message = MessageConstants.VERIFICATION_CODE_CAN_NOT_BE_NULL)
    private String code;
}
