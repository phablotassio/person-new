package com.person.api.mapper;

import com.person.api.dto.*;
import com.person.api.model.PersonEntity;
import com.person.api.util.DocumentUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class PersonMapper {

    public abstract PersonEntity toEntity(PersonRequestDTO personRequestDTO);

    public abstract PersonRequestDTO toRequest(PersonEntity personEntity);

    @Mapping(source = "addressResponseDTO", target = "address")
    @Mapping(source = "telephoneDTO", target = "phone")
    @Mapping(source = "weightAndHeightDTO.heightDTO", target = "height")
    @Mapping(source = "weightAndHeightDTO.weightDTO", target = "weight")
    @Mapping(source = "personEntity.documentNumber", target = "documentNumber", qualifiedByName = "mappingCpfResponse")
    public abstract PersonResponseDTO toResponse(PersonEntity personEntity, AddressDTO addressResponseDTO, TelephoneDTO telephoneDTO, WeightAndHeightDTO weightAndHeightDTO);


    @Named("mappingCpfResponse")
    public String mappingCpfResponse(String cpf) {
        return DocumentUtils.maskCPF(cpf);
    }
}
