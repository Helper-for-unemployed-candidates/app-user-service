package com.jobhunter.appuserservice.mapper;

import com.jobhunter.appuserservice.entities.Company;
import com.jobhunter.appuserservice.payload.CompanyCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CompanyMapper {
    Company toCompany(CompanyCreateDTO companyCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateCompanyForSignUp(@MappingTarget Company company, CompanyCreateDTO companyCreateDTO);
}
