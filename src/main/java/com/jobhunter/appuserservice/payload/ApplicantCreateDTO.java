package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApplicantCreateDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDate;
    private Gender gender;
}
