package com.jobhunter.appuserservice.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDTO {
    private String companyName;
    private String companySphere;
    private String aboutCompany;
    private String companyLicense;
    private String officialWebsite;
}
