package com.person.api.mapper;

import com.person.api.dto.*;
import com.person.api.model.PersonEntity;
import com.person.api.model.enums.SexType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.spy;

class PersonMapperTest {

    private final PersonMapper mapper = Mappers.getMapper(PersonMapper.class);

    @Test
    void toEntity() {

        PersonRequestDTO personRequestDTO = spy(PersonRequestDTO.class);

        personRequestDTO.setFullName("Alguem da silva");
        personRequestDTO.setFathersName("Brincante");
        personRequestDTO.setMothersName("Roselia");
        personRequestDTO.setSexType(SexType.M);
        personRequestDTO.setBirthDate(LocalDate.parse("1997-08-27"));
        personRequestDTO.setEmail("alguem@foo.com.br");
        personRequestDTO.setDocumentNumber("879.987.987-99");

        PersonEntity personEntity = mapper.toEntity(personRequestDTO);

        assertEquals("Alguem da silva", personEntity.getFullName());
        assertEquals("Brincante", personEntity.getFathersName());
        assertEquals("Roselia", personEntity.getMothersName());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals(LocalDate.parse("1997-08-27"), personEntity.getBirthDate());
        assertEquals("alguem@foo.com.br", personEntity.getEmail());
        assertEquals("879.987.987-99", personEntity.getDocumentNumber());

    }

    @Test
    void toEntityWithParamNull() {


        PersonEntity personEntity = mapper.toEntity(null);

        assertNull(personEntity);

    }

    @Test
    void toResponseWithAddress() {

        PersonEntity personEntity = spy(PersonEntity.class);

        personEntity.setFullName("Alguem da silva");
        personEntity.setFathersName("Brincante");
        personEntity.setMothersName("Roselia");
        personEntity.setSexType(SexType.M);
        personEntity.setBirthDate(LocalDate.parse("1997-08-27"));
        personEntity.setEmail("alguem@foo.com.br");
        personEntity.setDocumentNumber("879.987.987-99");
        personEntity.setUuid("2d5457f9-3d78-48a3-986c-831210a340c5");

        AddressDTO addressDTO = spy(AddressDTO.class);
        addressDTO.setZipCode("73080-100");
        addressDTO.setStreet("QMS 55A");
        addressDTO.setNeighborhood("Setor de Mansões de Sobradinho");
        addressDTO.setCity("Brasília");
        addressDTO.setState("DF");
        addressDTO.setComplement("La manos");

        TelephoneDTO telephoneDTO = spy(TelephoneDTO.class);
        telephoneDTO.setNumber("61996585451");

        HeightDTO heightDTO = spy(HeightDTO.class);
        heightDTO.setHeight("1.85");

        WeightDTO weightDTO = spy(WeightDTO.class);
        weightDTO.setWeight("62");

        WeightAndHeightDTO weightAndHeightDTO = new WeightAndHeightDTO(weightDTO, heightDTO);

        PersonResponseDTO personResponseDTO = mapper.toResponse(personEntity, addressDTO, telephoneDTO, weightAndHeightDTO);

        assertEquals("Alguem da silva", personResponseDTO.getFullName());
        assertEquals("2d5457f9-3d78-48a3-986c-831210a340c5", personResponseDTO.getUuid());
        assertEquals("Brincante", personResponseDTO.getFathersName());
        assertEquals("Roselia", personResponseDTO.getMothersName());
        assertEquals(SexType.M, personResponseDTO.getSexType());
        assertEquals(LocalDate.parse("1997-08-27"), personResponseDTO.getBirthDate());
        assertEquals("alguem@foo.com.br", personResponseDTO.getEmail());
        assertEquals("879.987.987-99", personResponseDTO.getDocumentNumber());
        assertEquals("73080-100", personResponseDTO.getAddress().getZipCode());
        assertEquals("QMS 55A", personResponseDTO.getAddress().getStreet());
        assertEquals("Setor de Mansões de Sobradinho", personResponseDTO.getAddress().getNeighborhood());
        assertEquals("Brasília", personResponseDTO.getAddress().getCity());
        assertEquals("DF", personResponseDTO.getAddress().getState());
        assertEquals("La manos", personResponseDTO.getAddress().getComplement());
        assertEquals("61996585451", personResponseDTO.getPhone().getNumber());
        assertEquals("1.85", personResponseDTO.getHeight().getHeight());
        assertEquals("62", personResponseDTO.getWeight().getWeight());

    }

    @Test
    void toResponseWithAddressAndPersonEntityNull() {


        TelephoneDTO telephoneDTO = spy(TelephoneDTO.class);
        telephoneDTO.setNumber("61996585451");


        PersonResponseDTO personResponseDTO = mapper.toResponse(null, null, telephoneDTO, null);

        assertEquals("61996585451", telephoneDTO.getNumber());

    }

    @Test
    void toResponseWithAddressAndPhoneAndHeightNull() {

        PersonEntity personEntity = spy(PersonEntity.class);

        personEntity.setFullName("Alguem da silva");
        personEntity.setFathersName("Brincante");
        personEntity.setMothersName("Roselia");
        personEntity.setSexType(SexType.M);
        personEntity.setBirthDate(LocalDate.parse("1997-08-27"));
        personEntity.setEmail("alguem@foo.com.br");
        personEntity.setDocumentNumber("879.987.987-99");

        PersonResponseDTO personResponseDTO = mapper.toResponse(personEntity, null, null, null);

        assertEquals("Alguem da silva", personResponseDTO.getFullName());
        assertEquals("Brincante", personResponseDTO.getFathersName());
        assertEquals("Roselia", personResponseDTO.getMothersName());
        assertEquals(SexType.M, personResponseDTO.getSexType());
        assertEquals(LocalDate.parse("1997-08-27"), personResponseDTO.getBirthDate());
        assertEquals("alguem@foo.com.br", personResponseDTO.getEmail());
        assertEquals("879.987.987-99", personResponseDTO.getDocumentNumber());
        assertNull(personResponseDTO.getAddress());


    }


    @Test
    void toResponseWithPersonEntityAndPhoneAndHeightNull() {

        AddressDTO addressDTO = spy(AddressDTO.class);
        addressDTO.setZipCode("73080-100");
        addressDTO.setStreet("QMS 55A");
        addressDTO.setNeighborhood("Setor de Mansões de Sobradinho");
        addressDTO.setCity("Brasília");
        addressDTO.setState("DF");
        addressDTO.setComplement("La manos");


        PersonResponseDTO personResponseDTO = mapper.toResponse(null, addressDTO, null, null);


        assertNull(personResponseDTO.getFullName());
        assertNull(personResponseDTO.getFathersName());
        assertNull(personResponseDTO.getMothersName());
        assertNull(personResponseDTO.getSexType());
        assertNull(personResponseDTO.getBirthDate());
        assertNull(personResponseDTO.getEmail());
        assertNull(personResponseDTO.getDocumentNumber());
        assertEquals("73080-100", personResponseDTO.getAddress().getZipCode());
        assertEquals("QMS 55A", personResponseDTO.getAddress().getStreet());
        assertEquals("Setor de Mansões de Sobradinho", personResponseDTO.getAddress().getNeighborhood());
        assertEquals("Brasília", personResponseDTO.getAddress().getCity());
        assertEquals("DF", personResponseDTO.getAddress().getState());
        assertEquals("La manos", personResponseDTO.getAddress().getComplement());

    }


    @Test
    void toResponseWithNullParams() {


        PersonResponseDTO personResponseDTO = mapper.toResponse(null, null, null, null);

        assertNull(personResponseDTO);


    }

    @Test
    void toRequest() {

        PersonEntity personEntity = spy(PersonEntity.class);

        personEntity.setFullName("Alguem da silva");
        personEntity.setFathersName("Brincante");
        personEntity.setMothersName("Roselia");
        personEntity.setSexType(SexType.M);
        personEntity.setBirthDate(LocalDate.parse("1997-08-27"));
        personEntity.setEmail("alguem@foo.com.br");
        personEntity.setDocumentNumber("879.987.987-99");

        PersonRequestDTO personRequestDTO = mapper.toRequest(personEntity);

        assertEquals("Alguem da silva", personRequestDTO.getFullName());
        assertEquals("Brincante", personRequestDTO.getFathersName());
        assertEquals("Roselia", personRequestDTO.getMothersName());
        assertEquals(SexType.M, personRequestDTO.getSexType());
        assertEquals(LocalDate.parse("1997-08-27"), personRequestDTO.getBirthDate());
        assertEquals("alguem@foo.com.br", personRequestDTO.getEmail());
        assertEquals("879.987.987-99", personRequestDTO.getDocumentNumber());

    }

    @Test
    void toRequestWithNullPersonEntity() {


        PersonRequestDTO personRequestDTO = mapper.toRequest(null);

        assertNull(personRequestDTO);


    }
}