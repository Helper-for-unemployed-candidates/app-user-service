package com.jobhunter.appuserservice.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    @Schema(description = "Region where the address is located", example = "Tashkent")
    private String region;

    @Schema(description = "City where the address is located", example = "Tashkent")
    private String city;

    @Schema(description = "Full address including street, building number, etc.", example = "1st Street, Building 10, Apt 5")
    private String fullAddress;
}
