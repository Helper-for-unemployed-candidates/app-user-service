package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailUpdateDTO {
    @NotBlank(message = MessageConstants.EMAIL_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = RestConstants.EMAIL_REGEX, message = MessageConstants.EMAIL_SHOULD_MATCH_REGEX)
    private String email;
}
