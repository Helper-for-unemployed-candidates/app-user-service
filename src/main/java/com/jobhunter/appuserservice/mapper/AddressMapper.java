package com.jobhunter.appuserservice.mapper;

import com.jobhunter.appuserservice.entities.Address;
import com.jobhunter.appuserservice.payload.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    @Mapping(target = "city", expression = "java( address.getCity().getName() )")
    @Mapping(target = "region", expression = "java( address.getCity().getRegion().name() )")
    AddressDTO toAddressDTO(Address address);
}
