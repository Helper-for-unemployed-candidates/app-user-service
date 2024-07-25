package com.jobhunter.appuserservice.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RegionDTO {
    private String name;
    private List<CityDTO> cities;
}
