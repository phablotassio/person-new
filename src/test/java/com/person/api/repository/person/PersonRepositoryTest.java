package com.person.api.repository.person;

import com.person.api.model.PersonEntity;
import com.person.api.model.enums.SexType;
import com.person.api.model.filter.PersonFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Mock
    private Pageable pageable;

    @BeforeEach
    public void setup() {

        when(pageable.getPageNumber()).thenReturn(0);
        when(pageable.isPaged()).thenReturn(Boolean.TRUE);
        when(pageable.getPageSize()).thenReturn(5);
        when(pageable.getSort()).thenReturn(Sort.unsorted());
    }


    @Test
    void findAllAndFilterWithAllFilters() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setFullName("brinca");
        personFilter.setDocumentNumber("52278512048");
        personFilter.setFathersName("jos");
        personFilter.setMothersName("rosel");
        personFilter.setInitBirthDate(LocalDate.parse("1996-05-22").toString());
        personFilter.setEndBirthDate(LocalDate.parse("1996-05-22").toString());
        personFilter.setSexType(SexType.M);
        personFilter.setEmail("brancante@foo.com");
        personFilter.setZipCode("73080100");
        personFilter.setCity("Brasilia");
        personFilter.setState("df");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertEquals(1, persons.size());

        PersonEntity personEntity = persons.get(0);


        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());

    }


    @Test
    void findAllAndFilterPerFullName() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setFullName("brinca");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertEquals(1, persons.size());

        PersonEntity personEntity = persons.get(0);


        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());

    }


    @Test
    void findAllAndFilterPerDocumentNumber() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setDocumentNumber("52278512048");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertEquals(1, persons.size());

        PersonEntity personEntity = persons.get(0);


        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());

    }


    @Test
    void findAllAndFilterPerFathersName() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setFathersName("jos");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertEquals(1, persons.size());

        PersonEntity personEntity = persons.get(0);


        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());

    }


    @Test
    void findAllAndFilterPerMothersName() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setMothersName("rosel");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertEquals(1, persons.size());

        PersonEntity personEntity = persons.get(0);


        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());

    }

    @Test
    void findAllAndFilterPerBirthDate() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setInitBirthDate(LocalDate.parse("1993-05-22").toString());
        personFilter.setEndBirthDate(LocalDate.parse("1996-05-22").toString());

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertEquals(1, persons.size());

        PersonEntity personEntity = persons.get(0);


        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());

    }

    @Test
    void findAllAndFilterPerBirthDateWithDatesIncorrect() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setInitBirthDate(LocalDate.parse("1998-05-22").toString());
        personFilter.setEndBirthDate(LocalDate.parse("1993-05-28").toString());

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertTrue(persons.isEmpty());

    }


    @Test
    void findAllAndFilterPerSexType() {

        PersonFilter personFilter = new PersonFilter();


        personFilter.setSexType(SexType.M);

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertEquals(5, persons.size());


        PersonEntity personEntity = persons.get(0);

        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());


        PersonEntity secondPersonEntity = persons.get(1);

        assertEquals("mister", secondPersonEntity.getFullName());
        assertEquals("15286351085", secondPersonEntity.getDocumentNumber());
        assertEquals("joao", secondPersonEntity.getFathersName());
        assertEquals("maria", secondPersonEntity.getMothersName());
        assertEquals(LocalDate.parse("1998-03-24"), secondPersonEntity.getBirthDate());
        assertEquals(SexType.M, secondPersonEntity.getSexType());
        assertEquals("brancantedasilva@foo.com", secondPersonEntity.getEmail());


    }

    @Test
    void findAllAndFilterPerEmail() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setEmail("brancante@foo.com");


        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertEquals(1, persons.size());

        PersonEntity personEntity = persons.get(0);


        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());

    }

    @Test
    void findAllAndFilterPerEmailSamePerson() {

        PersonFilter personFilter = new PersonFilter();
        personFilter.setId(106L);

        personFilter.setEmail("haha@foo.com");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertTrue(persons.isEmpty());

    }


    @Test
    void findAllAndFilterPerIncorrectEmail() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setEmail("foo@email.com");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertTrue(persons.isEmpty());


    }

    @Test
    void findAllAndFilterPerZipCode() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setZipCode("73080100");


        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertEquals(2, persons.size());

        PersonEntity personEntity = persons.get(0);

        assertEquals(101, personEntity.getId());
        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());


        PersonEntity secondPersonEntity = persons.get(1);

        assertEquals(105, secondPersonEntity.getId());
        assertEquals("jose augusto", secondPersonEntity.getFullName());
        assertEquals("88872152003", secondPersonEntity.getDocumentNumber());
        assertEquals("Luciano", secondPersonEntity.getFathersName());
        assertEquals("carla", secondPersonEntity.getMothersName());
        assertEquals(LocalDate.parse("2018-11-24"), secondPersonEntity.getBirthDate());
        assertEquals(SexType.M, secondPersonEntity.getSexType());
        assertEquals("pedacinho@foo.com", secondPersonEntity.getEmail());

    }

    @Test
    void findAllAndFilterPerIncorrectZipCode() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setZipCode("093219089321");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertTrue(persons.isEmpty());

    }

    @Test
    void findAllAndFilterPerCity() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setCity("brasi");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertEquals(2, persons.size());

        PersonEntity personEntity = persons.get(0);


        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());

    }

    @Test
    void findAllAndFilterPerState() {

        PersonFilter personFilter = new PersonFilter();

        personFilter.setState("df");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertEquals(2, persons.size());

        PersonEntity personEntity = persons.get(0);

        assertEquals(101L, personEntity.getId());
        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());

        PersonEntity secondPersonEntity = persons.get(1);

        assertEquals(105, secondPersonEntity.getId());
        assertEquals("jose augusto", secondPersonEntity.getFullName());
        assertEquals("88872152003", secondPersonEntity.getDocumentNumber());
        assertEquals("Luciano", secondPersonEntity.getFathersName());
        assertEquals("carla", secondPersonEntity.getMothersName());
        assertEquals(LocalDate.parse("2018-11-24"), secondPersonEntity.getBirthDate());
        assertEquals(SexType.M, secondPersonEntity.getSexType());
        assertEquals("pedacinho@foo.com", secondPersonEntity.getEmail());

    }


    @Test
    void findAllAndWithIncorrectNameInFilter() {

        PersonFilter personFilter = new PersonFilter();
        personFilter.setFullName("pedacaozera");
        personFilter.setDocumentNumber("52278512048");
        personFilter.setFathersName("jos");
        personFilter.setMothersName("rosel");
        personFilter.setInitBirthDate(LocalDate.parse("1996-05-22").toString());
        personFilter.setEndBirthDate(LocalDate.parse("1996-05-22").toString());
        personFilter.setSexType(SexType.M);
        personFilter.setEmail("brancante@foo.com");
        personFilter.setZipCode("73080100");
        personFilter.setCity("Brasilia");
        personFilter.setState("df");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertTrue(persons.isEmpty());

    }


    @Test
    void findAllAndFilterWithLimit() {

        when(pageable.getPageSize()).thenReturn(0);

        PersonFilter personFilter = new PersonFilter();
        personFilter.setFullName("pedacaozera");
        personFilter.setDocumentNumber("52278512048");
        personFilter.setFathersName("jos");
        personFilter.setMothersName("rosel");
        personFilter.setInitBirthDate(LocalDate.parse("1996-05-22").toString());
        personFilter.setEndBirthDate(LocalDate.parse("1996-05-22").toString());
        personFilter.setSexType(SexType.M);
        personFilter.setEmail("brancante@foo.com");
        personFilter.setZipCode("73080100");
        personFilter.setCity("Brasilia");
        personFilter.setState("df");

        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);

        assertTrue(persons.isEmpty());

    }


    @Test
    void findAllAndFilterWithNullFilter() {


        List<PersonEntity> persons = personRepository.findAllAndFilter(null, pageable);

        assertEquals(5, persons.size());

        PersonEntity personEntity = persons.get(0);

        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());


        PersonEntity secondPersonEntity = persons.get(1);

        assertEquals("mister", secondPersonEntity.getFullName());
        assertEquals("15286351085", secondPersonEntity.getDocumentNumber());
        assertEquals("joao", secondPersonEntity.getFathersName());
        assertEquals("maria", secondPersonEntity.getMothersName());
        assertEquals(LocalDate.parse("1998-03-24"), secondPersonEntity.getBirthDate());
        assertEquals(SexType.M, secondPersonEntity.getSexType());
        assertEquals("brancantedasilva@foo.com", secondPersonEntity.getEmail());

    }


    @Test
    void findAllAndFilterWithoutFilter() {

        PersonFilter personFilter = new PersonFilter();


        List<PersonEntity> persons = personRepository.findAllAndFilter(personFilter, pageable);


        assertEquals(5, persons.size());


        PersonEntity personEntity = persons.get(0);

        assertEquals("brincante da silva", personEntity.getFullName());
        assertEquals("52278512048", personEntity.getDocumentNumber());
        assertEquals("jose", personEntity.getFathersName());
        assertEquals("roselia", personEntity.getMothersName());
        assertEquals(LocalDate.parse("1996-05-22"), personEntity.getBirthDate());
        assertEquals(SexType.M, personEntity.getSexType());
        assertEquals("brancante@foo.com", personEntity.getEmail());


        PersonEntity secondPersonEntity = persons.get(1);

        assertEquals("mister", secondPersonEntity.getFullName());
        assertEquals("15286351085", secondPersonEntity.getDocumentNumber());
        assertEquals("joao", secondPersonEntity.getFathersName());
        assertEquals("maria", secondPersonEntity.getMothersName());
        assertEquals(LocalDate.parse("1998-03-24"), secondPersonEntity.getBirthDate());
        assertEquals(SexType.M, secondPersonEntity.getSexType());
        assertEquals("brancantedasilva@foo.com", secondPersonEntity.getEmail());


    }

    @Test
    void findByUuid() {

        Optional<PersonEntity> byUuid = personRepository.findByUuid("9209786f-c1ed-4014-a3fe-5601aacee291");

        assertTrue(byUuid.isPresent());

        PersonEntity personEntity = byUuid.get();

        assertEquals(101L, personEntity.getId());
        assertEquals("9209786f-c1ed-4014-a3fe-5601aacee291", personEntity.getUuid());


    }

    @Test
    void findByUuidNotFound() {

        Optional<PersonEntity> byUuid = personRepository.findByUuid("45b7d9a5-c2f1-4332-9e4e-d11733f67c99");

        assertFalse(byUuid.isPresent());


    }
}