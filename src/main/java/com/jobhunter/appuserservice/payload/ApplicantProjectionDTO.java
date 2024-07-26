package com.jobhunter.appuserservice.payload;

import com.jobhunter.appuserservice.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ApplicantProjectionDTO {

    @Schema(description = "First name of the applicant", example = "John")
    private String firstName;

    @Schema(description = "Middle name of the applicant", example = "A")
    private String middleName;

    @Schema(description = "Last name of the applicant", example = "Doe")
    private String lastName;

    @Schema(description = "Birth date of the applicant", example = "1990-01-01")
    private Date birthDate;

    @Schema(description = "Gender of the applicant", example = "MALE")
    private Gender gender;

    @Schema(description = "Whether the applicant is blocked or not", example = "false")
    private boolean blocked;

    @Schema(description = "Whether the applicant is enabled or not", example = "true")
    private boolean enabled;
}
