package com.person.api.mapper;

import com.person.api.dto.HeightDTO;
import com.person.api.dto.WeightAndHeightDTO;
import com.person.api.dto.WeightDTO;
import com.person.api.model.PersonEntity;
import com.person.api.model.WeighAndHeightEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.spy;

public class WeighAndHeightRepositoryMapperTest {

    private WeighAndHeightMapper mapper = Mappers.getMapper(WeighAndHeightMapper.class);

    @Test
    void toWeightResponseDTO() {

        WeighAndHeightEntity weighAndHeightEntity = spy(WeighAndHeightEntity.class);
        weighAndHeightEntity.setWeight("68");
        weighAndHeightEntity.setHeight("1.77");

        WeightAndHeightDTO weightDTO = mapper.toResponse(weighAndHeightEntity);

        assertEquals("1.77", weightDTO.getHeightDTO().getHeight());
        assertEquals("68", weightDTO.getWeightDTO().getWeight());
    }

    @Test
    void toWeightResponseDtoWithNullParam() {

        WeightAndHeightDTO weightDTO = mapper.toResponse(null);

        assertNull(weightDTO);
    }

    @Test
    void toWeightEntity() {

        WeightDTO weightDTO = new WeightDTO("68");

        HeightDTO heightDTO = new HeightDTO("1.77");


        WeightAndHeightDTO weightAndHeightDTO = new WeightAndHeightDTO(weightDTO, heightDTO);

        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(18L);
        personEntity.setUuid("d2a46f7c-f0d4-434c-9901-5b7d2d1ec6db");

        WeighAndHeightEntity weighAndHeightEntity = mapper.toWeighAndHeight(weightAndHeightDTO, personEntity);

        assertNull(weighAndHeightEntity.getId());
        assertEquals("68", weighAndHeightEntity.getWeight());
        assertNull(weighAndHeightEntity.getUuid());
        assertEquals("1.77", weighAndHeightEntity.getHeight());
        assertEquals(personEntity, weighAndHeightEntity.getPersonEntity());
    }

    @Test
    void toWeightEntityWithWeightNull() {

        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(18L);

        WeighAndHeightEntity weighAndHeightEntity = mapper.toWeighAndHeight(null, personEntity);

        assertNull(weighAndHeightEntity.getId());
        assertNull(weighAndHeightEntity.getWeight());
        assertEquals(personEntity, weighAndHeightEntity.getPersonEntity());

    }

    @Test
    void toWeightEntityWithPersonNull() {

        WeightDTO weightDTO = new WeightDTO("68");

        HeightDTO heightDTO = new HeightDTO("1.77");


        WeightAndHeightDTO weightAndHeightDTO = new WeightAndHeightDTO(weightDTO, heightDTO);


        WeighAndHeightEntity weighAndHeightEntity = mapper.toWeighAndHeight(weightAndHeightDTO, null);

        assertNull(weighAndHeightEntity.getId());
        assertNull(weighAndHeightEntity.getPersonEntity());
        assertEquals("68", weighAndHeightEntity.getWeight());
    }
}
