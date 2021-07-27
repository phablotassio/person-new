package com.person.api.repository;

import com.person.api.dto.TelephoneDTO;
import com.person.api.dto.TelephoneQueryDTO;
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
public class TelephoneRepositoryTest {

    @Autowired
    TelephoneRepository repository;

    @Test
    void getPhones() {

        Optional<TelephoneQueryDTO> optionalTelephoneDTO = repository.getByIdPerson(101L);

        assertTrue(optionalTelephoneDTO.isPresent());

        TelephoneDTO telephoneDTO = optionalTelephoneDTO.get();

        assertEquals("996585455", telephoneDTO.getNumber());
        assertEquals(61L, telephoneDTO.getAreaCode());
    }

    @Test
    void findByIdPersonWithoutResult() {

        Optional<TelephoneQueryDTO> optionalTelephoneDTO = repository.getByIdPerson(129L);

        assertFalse(optionalTelephoneDTO.isPresent());

    }
}
