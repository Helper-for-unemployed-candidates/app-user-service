package com.jobhunter.appuserservice.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CityDTO {
    @Schema(description = "ID of the city", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
    private UUID cityId;

    @Schema(description = "Name of the city", example = "Tashkent")
    private String name;
}
