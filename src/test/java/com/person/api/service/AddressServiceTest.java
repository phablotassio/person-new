package com.person.api.service;

import com.person.api.configuration.MessageSourceConfiguration;
import com.person.api.dto.AddressDTO;
import com.person.api.dto.AddressQueryDTO;
import com.person.api.exception.AbstractRuntimeException;
import com.person.api.mapper.AddressMapper;
import com.person.api.model.AddressEntity;
import com.person.api.model.PersonEntity;
import com.person.api.repository.AddressRepository;
import com.person.api.util.ValidateUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {MessageSourceConfiguration.class, ValidateUtils.class})
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressMapper mapper;

    @Mock
    private AddressRepository repository;

    @Test
    void processAddress() {

        AddressDTO addressDTO = spy(AddressDTO.class);
        PersonEntity personEntity = spy(PersonEntity.class);
        AddressEntity addressEntity = spy(AddressEntity.class);

        when(mapper.toAddressEntity(any(AddressDTO.class), any(PersonEntity.class))).thenReturn(addressEntity);
        when(repository.save(any(AddressEntity.class))).thenReturn(addressEntity);

        addressService.processAddress(addressDTO, personEntity);

        verify(mapper, times(1)).toAddressEntity(addressDTO, personEntity);
        verify(repository, times(1)).save(addressEntity);
        verify(mapper, times(1)).toAddressDTO(addressEntity);


    }


    @Test
    void processAddressWithoutAddress() {

        PersonEntity personEntity = spy(PersonEntity.class);

        addressService.processAddress(null, personEntity);

        verify(mapper, never()).toAddressEntity(any(), any());
        verify(repository, never()).save(any());
        verify(mapper, never()).toAddressDTO(any());

    }

    @Test
    void getAddressDTO() {

        AddressQueryDTO addressDTO = spy(AddressQueryDTO.class);

        when(repository.getByIdPerson(anyLong())).thenReturn(Optional.of(addressDTO));

        AddressDTO responseDto = addressService.getAddressDTO(127L);

        verify(repository, times(1)).getByIdPerson(127L);
        assertEquals(responseDto, addressDTO);
    }

    @Test
    void getAddressDTONotFound() {


        when(repository.getByIdPerson(anyLong())).thenReturn(Optional.empty());

        AddressDTO responseDto = addressService.getAddressDTO(127L);

        verify(repository, times(1)).getByIdPerson(127L);

        assertNull(responseDto);
    }

    @Test
    void update() {

        ArgumentCaptor<AddressQueryDTO> addressDTOArgumentCaptorTO = ArgumentCaptor.forClass(AddressQueryDTO.class);

        AddressQueryDTO addressDTO = spy(AddressQueryDTO.class);
        addressDTO.setZipCode("73080-100");
        addressDTO.setStreet("QMS 55A");
        addressDTO.setNeighborhood("Setor de Mansões de Sobradinho");
        addressDTO.setCity("Brasília");
        addressDTO.setState("DF");
        addressDTO.setComplement("La manos");

        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(132131L);

        Map<String, Object> changes = new HashMap<>();

        changes.put("zipCode", "73081-555");
        changes.put("street", "QMS 30A");
        changes.put("neighborhood", "sobradinho 2");
        changes.put("city", "sobraldisney");
        changes.put("state", "df");
        changes.put("complement", "mini preco");

        when(repository.getByIdPerson(anyLong())).thenReturn(Optional.of(addressDTO));

        AddressDTO updatedAddress = addressService.update(changes, personEntity);

        verify(repository, times(1)).getByIdPerson(132131L);
        verify(mapper, times(1)).toAddressEntity(addressDTOArgumentCaptorTO.capture(), eq(personEntity));

        assertEquals("73081-555", updatedAddress.getZipCode());
        assertEquals("QMS 30A", updatedAddress.getStreet());
        assertEquals("sobradinho 2", updatedAddress.getNeighborhood());
        assertEquals("sobraldisney", updatedAddress.getCity());
        assertEquals("df", updatedAddress.getState());
        assertEquals("mini preco", updatedAddress.getComplement());

    }


    @Test
    void updateWithAddressNull() {

        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(132131L);

        AddressQueryDTO addressDTO = new AddressQueryDTO();

        when(repository.getByIdPerson(anyLong())).thenReturn(Optional.of(addressDTO));

        AddressDTO updatedAddress = addressService.update(null, personEntity);

        verify(repository, times(1)).getByIdPerson(132131L);
        verify(mapper, never()).toAddressEntity(any(), any());

        assertEquals(addressDTO, updatedAddress);

    }

    @Test
    void updateAndValidateObjectWithAddressExist() {

        AddressQueryDTO addressDTO = spy(AddressQueryDTO.class);
        addressDTO.setZipCode("73080-100");
        addressDTO.setStreet("QMS 55A");
        addressDTO.setNeighborhood("Setor de Mansões de Sobradinho");
        addressDTO.setCity("Brasília");
        addressDTO.setState("DF");
        addressDTO.setComplement("La manos");

        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(132131L);


        Map<String, Object> changes = new HashMap<>();

        changes.put("zipCode", "73081-555");
        changes.put("street", "QMS 30A");
        changes.put("neighborhood", "sobradinho 2");
        changes.put("city", "sobraldisney");
        changes.put("state", "df");
        changes.put("complement", "mini preco");

        when(repository.getByIdPerson(anyLong())).thenReturn(Optional.of(addressDTO));

        AddressDTO updatedAddress = addressService.updateAndValidateObject(changes, personEntity);

        verify(repository, times(1)).getByIdPerson(132131L);


        assertEquals("73081-555", updatedAddress.getZipCode());
        assertEquals("QMS 30A", updatedAddress.getStreet());
        assertEquals("sobradinho 2", updatedAddress.getNeighborhood());
        assertEquals("sobraldisney", updatedAddress.getCity());
        assertEquals("df", updatedAddress.getState());
        assertEquals("mini preco", updatedAddress.getComplement());

    }


    @Test
    void updateAndValidateObjectWithAddressDontExist() {

        AddressQueryDTO addressDTO = spy(AddressQueryDTO.class);
        addressDTO.setZipCode("73080-100");
        addressDTO.setStreet("QMS 55A");
        addressDTO.setNeighborhood("Setor de Mansões de Sobradinho");
        addressDTO.setCity("Brasília");
        addressDTO.setState("DF");
        addressDTO.setComplement("La manos");

        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(132131L);


        Map<String, Object> changes = new HashMap<>();

        changes.put("zipCode", "73081-555");
        changes.put("street", "QMS 30A");
        changes.put("neighborhood", "sobradinho 2");
        changes.put("city", "sobraldisney");
        changes.put("state", "df");
        changes.put("complement", "mini preco");

        when(repository.getByIdPerson(anyLong())).thenReturn(Optional.empty());

        AddressDTO updatedAddress = addressService.updateAndValidateObject(changes, personEntity);

        verify(repository, times(1)).getByIdPerson(132131L);


        assertEquals("73081-555", updatedAddress.getZipCode());
        assertEquals("QMS 30A", updatedAddress.getStreet());
        assertEquals("sobradinho 2", updatedAddress.getNeighborhood());
        assertEquals("sobraldisney", updatedAddress.getCity());
        assertEquals("df", updatedAddress.getState());
        assertEquals("mini preco", updatedAddress.getComplement());

    }


    @Test
    void updateAndValidateObjectWithInvalidFields() {

        AddressQueryDTO addressDTO = spy(AddressQueryDTO.class);
        addressDTO.setZipCode("73080-100");
        addressDTO.setStreet("QMS 55A");
        addressDTO.setNeighborhood("Setor de Mansões de Sobradinho");
        addressDTO.setCity("Brasília");
        addressDTO.setState("DF");
        addressDTO.setComplement("La manos");

        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(132131L);


        Map<String, Object> changes = new HashMap<>();

        changes.put("zipCode", "73081-55532321321321");
        changes.put("street", "QMS 30A");
        changes.put("neighborhood", "sobradinho 2");
        changes.put("city", "sobraldisney");
        changes.put("state", "df");
        changes.put("complement", "mini preco");

        when(repository.getByIdPerson(anyLong())).thenReturn(Optional.of(addressDTO));

        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> addressService.updateAndValidateObject(changes, personEntity));

        verify(repository, times(1)).getByIdPerson(132131L);


        List<String> messages = abstractRuntimeException.getMessages();

        assertEquals(HttpStatus.BAD_REQUEST, abstractRuntimeException.getHttpStatus());
        assertFalse(messages.isEmpty());
        assertEquals(1, messages.size());
        assertTrue(messages.get(0).contains("zipCode"));
        assertTrue(abstractRuntimeException.getMessage().contains("zipCode"));

    }
}