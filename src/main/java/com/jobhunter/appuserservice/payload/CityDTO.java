package com.jobhunter.appuserservice.payload;

import jakarta.ws.rs.GET;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CityDTO {
    private UUID cityId;
    private String name;
}
