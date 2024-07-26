package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {
    @Schema(description = "Old password", example = "oldPassword123")
    @NotBlank(message = MessageConstants.OLD_PASSWORD_CAN_NOT_BE_EMPTY)
    private String oldPassword;

    @Schema(description = "New password", example = "newPassword123")
    @NotBlank(message = MessageConstants.PASSWORD_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = RestConstants.PASSWORD_REGEX, message = MessageConstants.PASSWORD_SHOULD_MATCH_REGEX)
    private String password;

    @Schema(description = "Confirm new password", example = "newPassword123")
    @NotBlank(message = MessageConstants.CONFIRM_PASSWORD_CAN_NOT_BE_EMPTY)
    private String confirmPassword;
}