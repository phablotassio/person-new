package com.person.api.service;

import com.person.api.dto.TelephoneDTO;
import com.person.api.dto.TelephoneQueryDTO;
import com.person.api.mapper.TelephoneMapper;
import com.person.api.model.PersonEntity;
import com.person.api.model.TelephoneEntity;
import com.person.api.repository.TelephoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelephoneServiceTest {

    @Mock
    private TelephoneRepository repository;

    @Mock
    private TelephoneMapper mapper;

    @InjectMocks
    private TelephoneService service;


    @Test
    void cratePhone() {
        TelephoneDTO telephoneDTO = spy(TelephoneDTO.class);
        TelephoneEntity telephoneEntity = spy(TelephoneEntity.class);
        PersonEntity personEntity = spy(PersonEntity.class);


        telephoneEntity.setNumber("99699999");
        telephoneEntity.setAreaCode(61L);

        when(mapper.toPhoneEntity(any(TelephoneDTO.class), any(PersonEntity.class))).thenReturn(telephoneEntity);
        when(repository.save(any(TelephoneEntity.class))).thenReturn(telephoneEntity);

        service.cratePhone(telephoneDTO, personEntity);

        verify(repository, times(1)).save(telephoneEntity);
        verify(mapper, times(1)).toPhoneEntity(telephoneDTO, personEntity);
    }

    @Test
    void testGetPhone() {

        TelephoneQueryDTO telephoneDTO = spy(TelephoneQueryDTO.class);

        when(repository.getByIdPerson(anyLong())).thenReturn(Optional.of(telephoneDTO));

        TelephoneDTO dto = service.getPhoneDTO(122L);

        verify(repository, times(1)).getByIdPerson(122L);
        assertEquals(telephoneDTO, dto);
    }

    @Test
    void findAll() {
        TelephoneDTO telephoneDTO = spy(TelephoneDTO.class);

        TelephoneEntity telephoneEntity = spy(TelephoneEntity.class);
        telephoneEntity.setId(1893L);

        TelephoneEntity secondTelephoneEntity = spy(TelephoneEntity.class);
        secondTelephoneEntity.setId(7575787L);

        List<TelephoneEntity> telephoneEntities = Arrays.asList(telephoneEntity, secondTelephoneEntity);

        when(repository.findAll()).thenReturn(telephoneEntities);

        service.getPhones();

        verify(repository, times(1)).findAll();

    }


    @Test
    void testGetPhoneWithNotFound() {

        when(repository.getByIdPerson(anyLong())).thenReturn(Optional.empty());

        TelephoneDTO responseDTO = service.getPhoneDTO(128L);

        verify(repository, times(1)).getByIdPerson(128L);

        assertNull(responseDTO);
    }
}