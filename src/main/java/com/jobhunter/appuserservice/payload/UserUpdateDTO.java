package com.jobhunter.appuserservice.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    @Schema(description = "Details of the company to be updated")
    private CompanyUpdateDTO company;

    @Schema(description = "Details of the applicant to be updated")
    private ApplicantUpdateDTO applicant;
}
