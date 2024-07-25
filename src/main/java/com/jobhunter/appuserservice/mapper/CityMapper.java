package com.jobhunter.appuserservice.mapper;

import com.jobhunter.appuserservice.entities.City;
import com.jobhunter.appuserservice.payload.CityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CityMapper {
    @Mapping(target = "cityId", source = "id")
    CityDTO toCityDTO(City city);

    List<CityDTO> toCityDTOs(List<City> cities);
}