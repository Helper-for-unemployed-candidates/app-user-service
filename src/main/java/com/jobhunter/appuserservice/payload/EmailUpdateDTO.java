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
public class EmailUpdateDTO {
    @Schema(description = "User's new email address", example = "user@example.com")
    @NotBlank(message = MessageConstants.EMAIL_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = RestConstants.EMAIL_REGEX, message = MessageConstants.EMAIL_SHOULD_MATCH_REGEX)
    private String email;
}