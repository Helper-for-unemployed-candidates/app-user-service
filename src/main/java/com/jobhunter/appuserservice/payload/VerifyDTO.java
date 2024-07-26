package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(description = "Data Transfer Object for verifying a user's account. One of email or phone has to null.")
public class VerifyDTO {

    @NotBlank(message = MessageConstants.VERIFICATION_CODE_ID_CAN_NOT_BE_NULL)
    @Schema(description = "Unique identifier for the verification code", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID codeId;

    @Pattern(regexp = RestConstants.EMAIL_REGEX, message = MessageConstants.EMAIL_SHOULD_MATCH_REGEX)
    @Schema(description = "Email address associated with the account", example = "user@example.com")
    private String email;

    @Pattern(regexp = RestConstants.UZB_PHONE_NUMBER_REGEX, message = MessageConstants.PHONE_NUMBER_SHOULD_MATCH_REGEX)
    @Schema(description = "Phone number associated with the account", example = "901234567")
    private String phoneNumber;

    @NotBlank(message = MessageConstants.VERIFICATION_CODE_CAN_NOT_BE_NULL)
    @Schema(description = "Verification code sent to the user", example = "123456")
    private String code;
}
