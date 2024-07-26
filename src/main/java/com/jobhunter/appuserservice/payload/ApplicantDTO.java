package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApplicantDTO {
    @Schema(description = "First name of the applicant", example = "John")
    private String firstName;

    @Schema(description = "Middle name of the applicant", example = "Michael")
    private String middleName;

    @Schema(description = "Last name of the applicant", example = "Doe")
    private String lastName;

    @Schema(description = "Date of birth of the applicant", example = "1990-01-01")
    private Date dateOfBirth;

    @Schema(description = "Gender of the applicant", example = "MALE")
    private Gender gender;
}
