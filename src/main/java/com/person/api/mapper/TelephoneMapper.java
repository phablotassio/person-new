package com.person.api.mapper;

import com.person.api.dto.TelephoneDTO;
import com.person.api.dto.TelephoneQueryDTO;
import com.person.api.model.PersonEntity;
import com.person.api.model.TelephoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class TelephoneMapper {

    public abstract TelephoneDTO toPhoneDTO(TelephoneEntity telephoneEntity);

    @Mapping(source = "personEntity", target = "personEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    public abstract TelephoneEntity toPhoneEntity(TelephoneDTO telephoneDTO, PersonEntity personEntity);

    @Mapping(source = "personEntity", target = "personEntity")
    @Mapping(source = "telephoneQueryDTO.id", target = "id")
    public abstract TelephoneEntity toPhoneEntity(TelephoneQueryDTO telephoneQueryDTO, PersonEntity personEntity);
}
