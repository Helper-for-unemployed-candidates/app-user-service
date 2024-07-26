package com.jobhunter.appuserservice.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CompanyUpdateDTO {
    @Schema(description = "Name of the company", example = "Example Ltd.")
    private String companyName;

    @Schema(description = "ID of the company's sphere of activity", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID companySphereId;

    @Schema(description = "Description about the company", example = "A leading provider of tech solutions.")
    private String aboutCompany;

    @Schema(description = "License number of the company", example = "LIC123456")
    private String companyLicense;

    @Schema(description = "Official website of the company", example = "https://www.example.com")
    private String officialWebsite;
}
