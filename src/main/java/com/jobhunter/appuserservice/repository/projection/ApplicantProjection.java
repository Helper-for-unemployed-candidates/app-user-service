package com.jobhunter.appuserservice.repository.projection;

import com.jobhunter.appuserservice.enums.Gender;

import java.util.Date;

public interface ApplicantProjection {
    String getFirstName();

    String getMiddleName();

    String getLastName();

    String getEmail();

    String getPhoneNumber();

    Date getBirthDate();

    Gender getGender();

    boolean getBlocked();

    boolean getEnabled();
}
