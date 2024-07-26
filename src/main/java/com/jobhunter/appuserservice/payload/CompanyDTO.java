package com.jobhunter.appuserservice.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDTO {
    @Schema(description = "Name of the company", example = "Example Inc.")
    private String companyName;

    @Schema(description = "Field or industry the company operates in", example = "Information Technology")
    private String companySphere;

    @Schema(description = "Brief description about the company", example = "A leading tech company specializing in innovative solutions.")
    private String aboutCompany;

    @Schema(description = "Company license number or identifier", example = "LIC123456789")
    private String companyLicense;

    @Schema(description = "Official website of the company", example = "https://www.example.com")
    private String officialWebsite;
}
