package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddressUpdateDTO {
    @NotBlank(message = MessageConstants.ADDRESS_CAN_NOT_BE_BLANK)
    private String fullAddress;
    @NotNull(message = MessageConstants.CITY_HAS_TO_BE_CHOSEN)
    private UUID cityId;
}
