package com.person.api.mapper;

import com.person.api.dto.WeightAndHeightDTO;
import com.person.api.model.PersonEntity;
import com.person.api.model.WeighAndHeightEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class WeighAndHeightMapper {


    @Mapping(source = "weighAndHeightEntity.weight", target = "weightDTO.weight")
    @Mapping(source = "weighAndHeightEntity.height", target = "heightDTO.height")
    public abstract WeightAndHeightDTO toResponse(WeighAndHeightEntity weighAndHeightEntity);

    @Mapping(source = "personEntity", target = "personEntity")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "weight", source = "weightAndHeightDTO.weightDTO.weight")
    @Mapping(target = "height", source = "weightAndHeightDTO.heightDTO.height")
    public abstract WeighAndHeightEntity toWeighAndHeight(WeightAndHeightDTO weightAndHeightDTO, PersonEntity personEntity);
}
