package com.person.api.mapper;

import com.person.api.dto.TelephoneDTO;
import com.person.api.model.PersonEntity;
import com.person.api.model.TelephoneEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.spy;

public class TelephoneMapperTest {

    private TelephoneMapper mapper = Mappers.getMapper(TelephoneMapper.class);

    @Test
    void toTelephoneResponseDTO() {
        TelephoneEntity telephoneEntity = spy(TelephoneEntity.class);
        telephoneEntity.setNumber("99999999");

        TelephoneDTO telephoneDTO = mapper.toPhoneDTO(telephoneEntity);

        assertEquals("99999999", telephoneDTO.getNumber());
    }

    @Test
    void toTelephoneResponseDtoWithNullParam() {

        TelephoneDTO telephoneDTO = mapper.toPhoneDTO(null);

        assertNull(telephoneDTO);
    }

    @Test
    void toTelephoneEntity() {
        TelephoneDTO telephoneDTO = spy(TelephoneDTO.class);
        telephoneDTO.setNumber("999999999");
        telephoneDTO.setAreaCode(61L);

        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(131L);
        personEntity.setUuid("d2a46f7c-f0d4-434c-9901-5b7d2d1ec6db");


        TelephoneEntity telephoneEntity = mapper.toPhoneEntity(telephoneDTO, personEntity);

        assertNull(telephoneEntity.getId());
        assertNull(telephoneEntity.getUuid());
        assertEquals(61, telephoneEntity.getAreaCode());
        assertEquals("999999999", telephoneEntity.getNumber());
        assertEquals(personEntity, telephoneEntity.getPersonEntity());
    }

    @Test
    void toTelephoneEntityWithTelephoneNull() {
        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(131L);

        TelephoneEntity telephoneEntity = mapper.toPhoneEntity(null, personEntity);

        assertNull(telephoneEntity.getNumber());
        assertEquals(personEntity, telephoneEntity.getPersonEntity());
    }

    @Test
    void toTelephoneEntityWithPersonNull() {
        TelephoneDTO telephoneDTO = spy(TelephoneDTO.class);
        telephoneDTO.setNumber("999999999");

        TelephoneEntity telephoneEntity = mapper.toPhoneEntity(telephoneDTO, null);


        assertEquals("999999999", telephoneEntity.getNumber());
        assertNull(telephoneEntity.getPersonEntity());
    }

}
