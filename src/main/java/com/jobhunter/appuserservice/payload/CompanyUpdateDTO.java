package com.jobhunter.appuserservice.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CompanyUpdateDTO {
    private String companyName;
    private UUID companySphereId;
    private String aboutCompany;
    private String companyLicense;
    private String officialWebsite;
}
