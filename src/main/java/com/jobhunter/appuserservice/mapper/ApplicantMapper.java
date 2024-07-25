package com.jobhunter.appuserservice.mapper;

import com.jobhunter.appuserservice.entities.Applicant;
import com.jobhunter.appuserservice.payload.ApplicantCreateDTO;
import com.jobhunter.appuserservice.payload.ApplicantDTO;
import com.jobhunter.appuserservice.payload.ApplicantUpdateDTO;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ApplicantMapper {
    Applicant toApplicant(ApplicantCreateDTO applicantCreateDTO);

    ApplicantDTO toApplicantDTO(Applicant applicant);

    void updateApplicantForSignUp(@MappingTarget Applicant applicant, ApplicantCreateDTO applicantCreateDTO);

    @IgnoreId
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    void update(@MappingTarget Applicant applicant, ApplicantUpdateDTO applicantUpdateDTO);
}
