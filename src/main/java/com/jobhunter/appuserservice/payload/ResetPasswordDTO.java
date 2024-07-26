package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "Data Transfer Object for resetting a user's password")
public class ResetPasswordDTO {
    @NotBlank(message = MessageConstants.EMAIL_OR_PHONE_NUMBER_CAN_NOT_BE_EMPTY)
    @Schema(description = "Username or phone number associated with the account", example = "user@example.com or 931234567")
    private String username;

    @Schema(description = "Verification code sent to the user", example = "123456")
    private String code;

    @Schema(description = "Unique identifier for the verification code", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID codeId;

    @NotBlank(message = MessageConstants.PASSWORD_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = RestConstants.PASSWORD_REGEX, message = MessageConstants.PASSWORD_SHOULD_MATCH_REGEX)
    @Schema(description = "New password for the account", example = "newP@ssw0rd123")
    private String password;

    @NotBlank(message = MessageConstants.CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY)
    @Schema(description = "Confirmation of the new password", example = "newP@ssw0rd123")
    private String confirmPassword;
}
