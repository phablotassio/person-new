package com.person.api.service;

import com.person.api.dto.WeightAndHeightDTO;
import com.person.api.dto.WeightDTO;
import com.person.api.mapper.WeighAndHeightMapper;
import com.person.api.model.PersonEntity;
import com.person.api.model.WeighAndHeightEntity;
import com.person.api.repository.WeighAndHeightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeighAndHeightRepositoryServiceTest {

    @Mock
    WeighAndHeightRepository repository;

    @Mock
    WeighAndHeightMapper mapper;

    @InjectMocks
    WeighAndHeightService service;

    @Test
    void createWeight() {

        WeighAndHeightEntity weighAndHeightEntity = spy(WeighAndHeightEntity.class);
        WeightAndHeightDTO weightDTO = new WeightAndHeightDTO("68", "1.77");

        PersonEntity personEntity = spy(PersonEntity.class);


        when(mapper.toWeighAndHeight(any(WeightAndHeightDTO.class), any(PersonEntity.class))).thenReturn(weighAndHeightEntity);
        when(repository.save(any(WeighAndHeightEntity.class))).thenReturn(weighAndHeightEntity);

        service.createWeightAnd(weightDTO, personEntity);

        verify(repository, times(1)).save(weighAndHeightEntity);
        verify(mapper, times(1)).toWeighAndHeight(weightDTO, personEntity);
        verify(mapper, times(1)).toResponse(weighAndHeightEntity);
    }

    @Test
    void getWheight() {

        WeightAndHeightDTO weightDTO = spy(WeightAndHeightDTO.class);

        when(repository.getByIdPerson(anyLong())).thenReturn(Optional.of(weightDTO));

        WeightAndHeightDTO dto = service.getWeight(101L);

        verify(repository, times(1)).getByIdPerson(101L);

        assertEquals(weightDTO, dto);
    }

    @Test
    void findAll() {

        WeightDTO weightDTO = spy(WeightDTO.class);

        WeighAndHeightEntity weighAndHeightEntity = spy(WeighAndHeightEntity.class);
        weighAndHeightEntity.setWeight("68");

        WeighAndHeightEntity secondWeigntEntity = spy(WeighAndHeightEntity.class);
        secondWeigntEntity.setWeight("68");

        List<WeighAndHeightEntity> listWeight = Arrays.asList(weighAndHeightEntity, secondWeigntEntity);


        when(repository.findAll()).thenReturn(listWeight);

        service.getWeights();

        verify(repository, times(1)).findAll();
    }
}
