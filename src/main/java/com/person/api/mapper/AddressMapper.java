package com.person.api.mapper;

import com.person.api.dto.AddressDTO;
import com.person.api.dto.AddressQueryDTO;
import com.person.api.model.AddressEntity;
import com.person.api.model.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AddressMapper {

    public abstract AddressDTO toAddressDTO(AddressEntity addressEntity);

    @Mapping(source = "personEntity", target = "personEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    public abstract AddressEntity toAddressEntity(AddressDTO addressDTO, PersonEntity personEntity);

    @Mapping(source = "personEntity", target = "personEntity")
    @Mapping(source = "addressDTO.id", target = "id")
    public abstract AddressEntity toAddressEntity(AddressQueryDTO addressDTO, PersonEntity personEntity);
}
