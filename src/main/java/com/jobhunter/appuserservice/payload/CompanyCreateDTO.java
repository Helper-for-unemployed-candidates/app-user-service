package com.jobhunter.appuserservice.payload;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CompanyCreateDTO {
    private String companyName;
    private UUID companySphereId;
    private String aboutCompany;
    private String companyLicense;
    private String officialWebsite;
}
