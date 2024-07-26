package com.jobhunter.appuserservice.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyProjectionDTO {

    @Schema(description = "Name of the company", example = "Example Inc")
    private String companyName;

    @Schema(description = "Business sphere of the company", example = "Software Development")
    private String companySphere;

    @Schema(description = "Description about the company", example = "Leading tech company specializing in software solutions.")
    private String aboutCompany;

    @Schema(description = "License of the company", example = "12345-67890")
    private String companyLicense;

    @Schema(description = "Official website of the company", example = "https://www.example.com")
    private String officialWebsite;

    @Schema(description = "Email address of the company", example = "contact@example.com")
    private String email;

    @Schema(description = "Phone number of the company", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Whether the company is blocked or not", example = "false")
    private boolean blocked;

    @Schema(description = "Whether the company is enabled or not", example = "true")
    private boolean enabled;
}
