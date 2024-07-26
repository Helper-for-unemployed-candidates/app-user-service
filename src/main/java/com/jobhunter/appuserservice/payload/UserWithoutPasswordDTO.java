package com.jobhunter.appuserservice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jobhunter.appuserservice.enums.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWithoutPasswordDTO {
    @Schema(description = "Unique identifier of the user", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "User's phone number", example = "+998901234567")
    private String phone;

    @Schema(description = "User's email address", example = "user@example.com")
    private String email;

    @Schema(description = "Unique identifier for the user's avatar", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID avatarId;

    @Schema(description = "Address details of the user")
    private AddressDTO address;

    @Schema(description = "Role assigned to the user.(APPLICANT, COMPANY OR ADMIN)", example = "APPLICANT")
    private RoleEnum role;

    @Schema(description = "Applicant details if the user is an applicant")
    private ApplicantDTO applicant;

    @Schema(description = "Company details if the user is a company")
    private CompanyDTO company;
}
