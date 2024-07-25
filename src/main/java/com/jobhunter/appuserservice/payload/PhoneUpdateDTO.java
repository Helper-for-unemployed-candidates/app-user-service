package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import com.jobhunter.appuserservice.utils.RestConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneUpdateDTO {
    @NotBlank(message = MessageConstants.PHONE_NUMBER_CAN_NOT_BE_EMPTY)
    @Pattern(regexp = RestConstants.UZB_PHONE_NUMBER_REGEX, message = MessageConstants.PHONE_NUMBER_SHOULD_MATCH_REGEX)
    private String phoneNumber;
}
