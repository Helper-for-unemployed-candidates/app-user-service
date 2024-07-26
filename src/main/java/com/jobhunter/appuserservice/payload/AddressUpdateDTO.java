package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.utils.MessageConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddressUpdateDTO {
    @Schema(description = "Full address of the user", example = "123 Main St, Apartment 4B")
    @NotBlank(message = MessageConstants.ADDRESS_CAN_NOT_BE_BLANK)
    private String fullAddress;

    @Schema(description = "ID of the city", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    @NotNull(message = MessageConstants.CITY_HAS_TO_BE_CHOSEN)
    private UUID cityId;
}