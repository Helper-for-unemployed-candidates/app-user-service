package com.jobhunter.appuserservice.mapper;

import com.jobhunter.appuserservice.entities.Company;
import com.jobhunter.appuserservice.payload.CompanyCreateDTO;
import com.jobhunter.appuserservice.payload.CompanyDTO;
import com.jobhunter.appuserservice.payload.CompanyUpdateDTO;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CompanyMapper {
    Company toCompany(CompanyCreateDTO companyCreateDTO);

    @Mapping(target = "companySphere", expression = "java( company.getCompanySphere().getName() )")
    CompanyDTO toCompanyDTO(Company company);

    @IgnoreId
    @Mapping(target = "user", ignore = true)
    void updateCompanyForSignUp(@MappingTarget Company company, CompanyCreateDTO companyCreateDTO);

    @IgnoreId
    @BeanMapping(
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateCompany(@MappingTarget Company company, CompanyUpdateDTO companyUpdateDTO);
}
