package com.jobhunter.appuserservice.mapper;

import com.jobhunter.appuserservice.entities.Applicant;
import com.jobhunter.appuserservice.payload.ApplicantCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ApplicantMapper {
    Applicant toApplicant(ApplicantCreateDTO applicantCreateDTO);

    void updateApplicantForSignUp(@MappingTarget Applicant applicant, ApplicantCreateDTO applicantCreateDTO);
}
