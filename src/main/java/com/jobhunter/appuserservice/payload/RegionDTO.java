package com.jobhunter.appuserservice.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RegionDTO {
    @Schema(description = "Name of the region", example = "Tashkent")
    private String name;

    @Schema(description = "List of cities in the region")
    private List<CityDTO> cities;
}