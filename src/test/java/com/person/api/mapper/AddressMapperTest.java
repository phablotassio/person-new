package com.person.api.mapper;

import com.person.api.dto.AddressDTO;
import com.person.api.model.AddressEntity;
import com.person.api.model.PersonEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.spy;

class AddressMapperTest {

    private AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    @Test
    void toAddressResponseDto() {

        AddressEntity addressEntity = spy(AddressEntity.class);
        addressEntity.setZipCode("73080-100");
        addressEntity.setStreet("QMS 55A");
        addressEntity.setNeighborhood("Setor de Mansões de Sobradinho");
        addressEntity.setCity("Brasília");
        addressEntity.setState("DF");
        addressEntity.setComplement("La manos");

        AddressDTO addressDTO = addressMapper.toAddressDTO(addressEntity);

        assertEquals("73080-100", addressDTO.getZipCode());
        assertEquals("QMS 55A", addressDTO.getStreet());
        assertEquals("Setor de Mansões de Sobradinho", addressDTO.getNeighborhood());
        assertEquals("Brasília", addressDTO.getCity());
        assertEquals("DF", addressDTO.getState());
        assertEquals("La manos", addressDTO.getComplement());


    }

    @Test
    void toAddressRequestDtoWithNullParam() {

        AddressDTO addressDTO = addressMapper.toAddressDTO(null);

        assertNull(addressDTO);


    }

    @Test
    void toAddressEntity() {

        AddressDTO addressDTO = spy(AddressDTO.class);
        addressDTO.setZipCode("73080-100");
        addressDTO.setStreet("QMS 55A");
        addressDTO.setNeighborhood("Setor de Mansões de Sobradinho");
        addressDTO.setCity("Brasília");
        addressDTO.setState("DF");
        addressDTO.setComplement("La manos");


        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(1321L);
        personEntity.setUuid("d2a46f7c-f0d4-434c-9901-5b7d2d1ec6db");


        AddressEntity addressEntity = addressMapper.toAddressEntity(addressDTO, personEntity);

        assertNull(addressEntity.getId());
        assertNull(addressEntity.getUuid());
        assertEquals("73080-100", addressEntity.getZipCode());
        assertEquals("QMS 55A", addressEntity.getStreet());
        assertEquals("Setor de Mansões de Sobradinho", addressEntity.getNeighborhood());
        assertEquals("Brasília", addressEntity.getCity());
        assertEquals("DF", addressEntity.getState());
        assertEquals("La manos", addressEntity.getComplement());
        assertEquals(personEntity, addressEntity.getPersonEntity());


    }


    @Test
    void toAddressEntityWithAddressNull() {


        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(1321L);

        AddressEntity addressEntity = addressMapper.toAddressEntity(null, personEntity);

        assertNull(addressEntity.getZipCode());
        assertNull(addressEntity.getStreet());
        assertNull(addressEntity.getNeighborhood());
        assertNull(addressEntity.getCity());
        assertNull(addressEntity.getState());
        assertNull(addressEntity.getComplement());
        assertEquals(personEntity, addressEntity.getPersonEntity());
    }


    @Test
    void toAddressEntityWithPersonNull() {

        AddressDTO addressDTO = spy(AddressDTO.class);
        addressDTO.setZipCode("73080-100");
        addressDTO.setStreet("QMS 55A");
        addressDTO.setNeighborhood("Setor de Mansões de Sobradinho");
        addressDTO.setCity("Brasília");
        addressDTO.setState("DF");
        addressDTO.setComplement("La manos");


        AddressEntity addressEntity = addressMapper.toAddressEntity(addressDTO, null);

        assertEquals("73080-100", addressEntity.getZipCode());
        assertEquals("QMS 55A", addressEntity.getStreet());
        assertEquals("Setor de Mansões de Sobradinho", addressEntity.getNeighborhood());
        assertEquals("Brasília", addressEntity.getCity());
        assertEquals("DF", addressEntity.getState());
        assertEquals("La manos", addressEntity.getComplement());
        assertNull(addressEntity.getPersonEntity());
    }


    @Test
    void toAddressEntityWithNullParams() {

        AddressEntity addressEntity = addressMapper.toAddressEntity(null, null);

        assertNull(addressEntity);
    }


}