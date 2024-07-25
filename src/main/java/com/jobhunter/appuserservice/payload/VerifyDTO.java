package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class VerifyDTO {
    @NotBlank(message = MessageConstants.VERIFICATION_CODE_ID_CAN_NOT_BE_NULL)
    private UUID codeId;
    @Pattern(regexp = RestConstants.EMAIL_REGEX, message = MessageConstants.EMAIL_SHOULD_MATCH_REGEX)
    private String email;
    @Pattern(regexp = RestConstants.UZB_PHONE_NUMBER_REGEX, message = MessageConstants.PHONE_NUMBER_SHOULD_MATCH_REGEX)
    private String phoneNumber;
    @NotBlank(message = MessageConstants.VERIFICATION_CODE_CAN_NOT_BE_NULL)
    private String code;
}
