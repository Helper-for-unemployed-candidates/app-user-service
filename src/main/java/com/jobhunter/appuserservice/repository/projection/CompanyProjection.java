package com.jobhunter.appuserservice.repository.projection;

import com.jobhunter.appuserservice.enums.Gender;

import java.util.Date;

public interface CompanyProjection {

    String getCompanyName();

    String getCompanySphere();

    String getAboutCompany();

    String getCompanyLicence();

    String getOfficialWebsite();

    String getEmail();

    String getPhoneNumber();

    boolean getBlocked();

    boolean getEnabled();
}
