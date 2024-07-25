package com.jobhunter.appuserservice.payload;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    CompanyUpdateDTO company;
    ApplicantUpdateDTO applicant;
}
