package com.person.api.repository;

import com.person.api.dto.AddressDTO;
import com.person.api.dto.AddressQueryDTO;
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
class AddressRepositoryTest {

    @Autowired
    private AddressRepository repository;

    @Test
    void findByIdPerson() {

        Optional<AddressQueryDTO> optionalAddressDTO = repository.getByIdPerson(101L);

        assertTrue(optionalAddressDTO.isPresent());

        AddressQueryDTO addressDTO = optionalAddressDTO.get();

        assertEquals(103, addressDTO.getId());
        assertEquals("Brasilia", addressDTO.getCity());
        assertEquals("la manos", addressDTO.getComplement());
        assertEquals("Setor de Mansoes de Sobradinho", addressDTO.getNeighborhood());
        assertEquals("qms 30a BSB", addressDTO.getStreet());
        assertEquals("73080100", addressDTO.getZipCode());


    }


    @Test
    void findByIdPersonWithoutResult() {

        Optional<AddressQueryDTO> addressDTO = repository.getByIdPerson(129L);

        assertFalse(addressDTO.isPresent());

    }


}