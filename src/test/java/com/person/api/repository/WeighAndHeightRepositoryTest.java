package com.person.api.repository;

import com.person.api.dto.WeightAndHeightDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class WeighAndHeightRepositoryTest {

    @Autowired
    WeighAndHeightRepository weighAndHeightRepository;

    @Test
    void getWeights() {

        Optional<WeightAndHeightDTO> optionalWeightDTO = weighAndHeightRepository.getByIdPerson(101L);

        assertTrue(optionalWeightDTO.isPresent());

        WeightAndHeightDTO weightDTO = optionalWeightDTO.get();

        assertEquals("65", weightDTO.getWeightDTO().getWeight());
        assertEquals("1.99", weightDTO.getHeightDTO().getHeight());
    }

    @Test
    void findByIdPersonWithoutResult() {

        Optional<WeightAndHeightDTO> optionalWeightDTO = weighAndHeightRepository.getByIdPerson(102L);

        assertFalse(optionalWeightDTO.isPresent());

    }
}
