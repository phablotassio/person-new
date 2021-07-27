package com.person.api.service;

import com.person.api.configuration.MessageSourceConfiguration;
import com.person.api.dto.*;
import com.person.api.exception.AbstractRuntimeException;
import com.person.api.exception.MessageErrorImpl;
import com.person.api.mapper.PersonMapper;
import com.person.api.model.PersonEntity;
import com.person.api.model.enums.SexType;
import com.person.api.model.filter.PersonFilter;
import com.person.api.repository.person.PersonRepository;
import com.person.api.util.ValidateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;

import static com.person.api.util.MessagesUtil.getMessage;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {MessageSourceConfiguration.class, ValidateUtils.class})
class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @Mock
    private PersonMapper mapper;

    @Mock
    private AddressService addressService;

    @Mock
    private TelephoneService telephoneService;


    @Mock
    private WeighAndHeightService weighAndHeightService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private PersonService service;

    @Test
    void createPerson() {

        AddressDTO addressDTO = spy(AddressDTO.class);

        PersonRequestDTO personRequestDTO = spy(PersonRequestDTO.class);
        personRequestDTO.setAddress(addressDTO);
        personRequestDTO.setDocumentNumber("137.075.680-16");
        personRequestDTO.setBirthDate(LocalDate.now().minusYears(5));


        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setEmail("foo@foo.com.br");

        HeightDTO heightDTO = spy(HeightDTO.class);
        TelephoneDTO telephoneDTO = spy(TelephoneDTO.class);
        WeightDTO weightDTO = spy(WeightDTO.class);

        personRequestDTO.setPhone(telephoneDTO);
        personRequestDTO.setHeight(heightDTO);
        personRequestDTO.setWeight(weightDTO);

        AddressDTO addressResponseDTO = spy(AddressDTO.class);

        TelephoneDTO phoneResponseDTO = spy(TelephoneDTO.class);

        WeightAndHeightDTO weightResponseDTO = spy(WeightAndHeightDTO.class);


        when(mapper.toEntity(any(PersonRequestDTO.class))).thenReturn(personEntity);
        when(repository.save(any(PersonEntity.class))).thenReturn(personEntity);
        when(addressService.processAddress(any(AddressDTO.class), any(PersonEntity.class))).thenReturn(addressResponseDTO);
        when(telephoneService.cratePhone(any(TelephoneDTO.class), any(PersonEntity.class))).thenReturn(phoneResponseDTO);
        when(weighAndHeightService.createWeightAnd(any(WeightAndHeightDTO.class), any(PersonEntity.class))).thenReturn(weightResponseDTO);

        service.createPerson(personRequestDTO);

        verify(repository, times(1)).save(personEntity);
        verify(mapper, times(1)).toEntity(personRequestDTO);
        verify(mapper, times(1)).toResponse(personEntity, addressResponseDTO, phoneResponseDTO, weightResponseDTO);
        verify(addressService, times(1)).processAddress(addressDTO, personEntity);

    }


    @Test
    void createPersonWithInvalidDocumentNumber() {

        AddressDTO addressDTO = spy(AddressDTO.class);

        PersonRequestDTO personRequestDTO = spy(PersonRequestDTO.class);
        personRequestDTO.setAddress(addressDTO);
        personRequestDTO.setDocumentNumber("777.777.777-77");

        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> service.createPerson(personRequestDTO));

        List<String> messages = abstractRuntimeException.getMessages();
        assertFalse(messages.isEmpty());
        assertEquals(1, messages.size());
        assertEquals(HttpStatus.BAD_REQUEST, abstractRuntimeException.getHttpStatus());
        assertEquals(getMessage(MessageErrorImpl.INVALID_DOCUMENT_NUMBER, "777.777.777-77"), abstractRuntimeException.getMessage());
        assertEquals(getMessage(MessageErrorImpl.INVALID_DOCUMENT_NUMBER, "777.777.777-77"), messages.get(0));

    }


    @Test
    void createPersonAlreadyRegistered() {

        ArgumentCaptor<PersonFilter> personFilter = ArgumentCaptor.forClass(PersonFilter.class);


        AddressDTO addressDTO = spy(AddressDTO.class);

        PersonRequestDTO personRequestDTO = spy(PersonRequestDTO.class);
        personRequestDTO.setAddress(addressDTO);
        personRequestDTO.setDocumentNumber("137.075.680-16");
        personRequestDTO.setEmail("foo@foozao.com");
        personRequestDTO.setBirthDate(LocalDate.now().minusYears(5));

        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setEmail("foo@foo.com.br");


        when(repository.findAllAndFilter(any(PersonFilter.class), any(Pageable.class))).thenReturn(Collections.singletonList(new PersonEntity()));

        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> service.createPerson(personRequestDTO));

        List<String> messages = abstractRuntimeException.getMessages();
        assertFalse(messages.isEmpty());
        assertEquals(2, messages.size());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, abstractRuntimeException.getHttpStatus());
        assertEquals(getMessage(MessageErrorImpl.RESOURCE_ALREADY_REGISTERED, getMessage(MessageErrorImpl.DOCUMENT_NUMBER, "137.075.680-16")), messages.get(0));
        assertEquals(getMessage(MessageErrorImpl.RESOURCE_ALREADY_REGISTERED, getMessage(MessageErrorImpl.EMAIL, "foo@foozao.com")), messages.get(1));

        verify(repository, times(2)).findAllAndFilter(personFilter.capture(), eq(Pageable.unpaged()));
        verify(repository, never()).save(personEntity);
        verify(mapper, never()).toEntity(personRequestDTO);
        verify(mapper, never()).toResponse(any(), any(), any(), any());
        verify(addressService, never()).processAddress(any(), any());

    }

    @Test
    void testGetPerson() {

        AddressDTO addressDTO = spy(AddressDTO.class);
        TelephoneDTO telephoneDTO = spy(TelephoneDTO.class);
        WeightAndHeightDTO weightDTO = spy(WeightAndHeightDTO.class);

        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(1893L);

        when(repository.findByUuid(anyString())).thenReturn(Optional.of(personEntity));
        when(addressService.getAddressDTO(anyLong())).thenReturn(addressDTO);
        when(telephoneService.getPhoneDTO(anyLong())).thenReturn(telephoneDTO);
        when(weighAndHeightService.getWeight(anyLong())).thenReturn(weightDTO);

        service.getPerson("9209786f-c1ed-4014-a3fe-5601aacee291");

        verify(repository, times(1)).findByUuid("9209786f-c1ed-4014-a3fe-5601aacee291");
        verify(addressService, times(1)).getAddressDTO(1893L);
        verify(mapper, times(1)).toResponse(personEntity, addressDTO, telephoneDTO, weightDTO);
    }


    @Test
    void testGetPersonWithNotFound() {


        when(repository.findByUuid(anyString())).thenReturn(Optional.empty());

        EntityNotFoundException notFoundException = Assertions.assertThrows(EntityNotFoundException.class, () -> service.getPerson("9209786f-c1ed-4014-a3fe-5601aacee29"));

        assertEquals(getMessage(MessageErrorImpl.RESOURCE_NOT_FOUND, getMessage(MessageErrorImpl.PERSON)), notFoundException.getMessage());

        verify(repository, times(1)).findByUuid("9209786f-c1ed-4014-a3fe-5601aacee29");

    }


    @Test
    void findAll() {

        PersonFilter personFilter = spy(PersonFilter.class);

        PersonEntity personEntity = spy(PersonEntity.class);
        personEntity.setId(1893L);

        PersonEntity secondPersonEntity = spy(PersonEntity.class);
        secondPersonEntity.setId(7575787L);

        AddressDTO addressDTO = spy(AddressDTO.class);

        AddressDTO secondAddressDTO = spy(AddressDTO.class);

        TelephoneDTO telephoneDTO = spy(TelephoneDTO.class);

        TelephoneDTO secondTelephoneDTO = spy(TelephoneDTO.class);

        WeightAndHeightDTO weightDTO = spy(WeightAndHeightDTO.class);

        WeightAndHeightDTO secondWeightDTO = spy(WeightAndHeightDTO.class);

        List<PersonEntity> personEntities = Arrays.asList(personEntity, secondPersonEntity);

        when(repository.findAllAndFilter(any(PersonFilter.class), any(Pageable.class))).thenReturn(personEntities);
        when(addressService.getAddressDTO(1893L)).thenReturn(addressDTO);
        when(addressService.getAddressDTO(7575787L)).thenReturn(secondAddressDTO);
        when(telephoneService.getPhoneDTO(1893L)).thenReturn(telephoneDTO);
        when(telephoneService.getPhoneDTO(7575787L)).thenReturn(secondTelephoneDTO);
        when(weighAndHeightService.getWeight(1893L)).thenReturn(weightDTO);
        when(weighAndHeightService.getWeight(7575787L)).thenReturn(secondWeightDTO);

        service.getPersons(personFilter, Pageable.unpaged());

        verify(addressService, times(1)).getAddressDTO(1893L);
        verify(addressService, times(1)).getAddressDTO(7575787L);

        verify(mapper, times(1)).toResponse(personEntity, addressDTO, telephoneDTO, weightDTO);
        verify(mapper, times(1)).toResponse(secondPersonEntity, secondAddressDTO, secondTelephoneDTO, secondWeightDTO);
        verify(repository, times(1)).findAllAndFilter(personFilter, Pageable.unpaged());


    }

    @Test
    void update() {

        ArgumentCaptor<PersonEntity> personEntityArgumentCaptor = ArgumentCaptor.forClass(PersonEntity.class);

        PersonEntity personEntity = spy(PersonEntity.class);

        personEntity.setFullName("Alguem da silva");
        personEntity.setFathersName("Brincante");
        personEntity.setMothersName("Roselia");
        personEntity.setSexType(SexType.M);
        personEntity.setBirthDate(LocalDate.parse("1997-08-27"));
        personEntity.setEmail("alguem@foo.com.br");
        personEntity.setDocumentNumber("879.987.987-99");

        PersonRequestDTO personRequestDTO = spy(PersonRequestDTO.class);

        personRequestDTO.setFullName("josefino");
        personRequestDTO.setFathersName("josefino");
        personRequestDTO.setMothersName("jessica");
        personRequestDTO.setSexType(SexType.F);
        personRequestDTO.setBirthDate(LocalDate.parse("2007-08-27"));
        personRequestDTO.setEmail("roberto@foo.com.br");
        personRequestDTO.setDocumentNumber("734.602.110-08");

        LinkedHashMap<String, Object> changesAddress = new LinkedHashMap<>();

        changesAddress.put("zipCode", "73080-100");
        changesAddress.put("street", "QMS 55A");
        changesAddress.put("neighborhood", "Setor de Mansões de Sobradinho");
        changesAddress.put("city", "Brasília");
        changesAddress.put("state", "DF");
        changesAddress.put("complement", "La manos");


        AddressDTO addressDTOResponseDTO = spy(AddressDTO.class);


        Map<String, Object> changes = new HashMap<>();

        changes.put("fullName", "josefino");
        changes.put("mothersName", "jessica");
        changes.put("fathersName", "roberto");
        changes.put("sexType", "F");
        changes.put("birthDate", "2007-08-27");
        changes.put("email", "roberto@foo.com.br");
        changes.put("documentNumber", "734.602.110-08");
        changes.put("address", changesAddress);


        when(repository.findByUuid(anyString())).thenReturn(Optional.of(personEntity));
        when(mapper.toRequest(any(PersonEntity.class))).thenReturn(personRequestDTO);
        when(addressService.update(anyMap(), any(PersonEntity.class))).thenReturn(addressDTOResponseDTO);

        service.update("9209786f-c1ed-4014-a3fe-5601aacee291", changes);


        verify(repository, times(1)).findByUuid("9209786f-c1ed-4014-a3fe-5601aacee291");
        verify(mapper, times(1)).toRequest(personEntityArgumentCaptor.capture());
        verify(repository, times(1)).save(personEntityArgumentCaptor.capture());
        verify(addressService, times(1)).update(eq(changesAddress), personEntityArgumentCaptor.capture());

        PersonEntity entityUpdated = personEntityArgumentCaptor.getValue();

        verify(mapper, times(1)).toResponse(personEntityArgumentCaptor.capture(), eq(addressDTOResponseDTO), eq(null), eq(null));

        assertEquals("josefino", entityUpdated.getFullName());
        assertEquals("roberto", entityUpdated.getFathersName());
        assertEquals("jessica", entityUpdated.getMothersName());
        assertEquals(SexType.F, entityUpdated.getSexType());
        assertEquals(LocalDate.parse("2007-08-27"), entityUpdated.getBirthDate());
        assertEquals("roberto@foo.com.br", entityUpdated.getEmail());
        assertEquals("734.602.110-08", entityUpdated.getDocumentNumber());


    }


    @Test
    void updateSomeProperties() {

        ArgumentCaptor<PersonEntity> personEntityArgumentCaptor = ArgumentCaptor.forClass(PersonEntity.class);

        PersonEntity personEntity = spy(PersonEntity.class);

        personEntity.setFullName("Alguem da silva");
        personEntity.setFathersName("Brincante");
        personEntity.setMothersName("Roselia");
        personEntity.setSexType(SexType.M);
        personEntity.setBirthDate(LocalDate.parse("1997-08-27"));
        personEntity.setEmail("alguem@foo.com.br");
        personEntity.setDocumentNumber("879.987.987-99");

        PersonRequestDTO personRequestDTO = spy(PersonRequestDTO.class);

        personRequestDTO.setFullName("josefino");
        personRequestDTO.setFathersName("roberto");
        personRequestDTO.setMothersName("jessica");
        personRequestDTO.setSexType(SexType.M);
        personRequestDTO.setBirthDate(LocalDate.parse("1997-08-27"));
        personRequestDTO.setEmail("alguem@foo.com.br");
        personRequestDTO.setDocumentNumber("879.987.987-99");

        Map<String, Object> changes = new HashMap<>();

        changes.put("fullName", "josefino");
        changes.put("mothersName", "jessica");
        changes.put("fathersName", "roberto");


        when(repository.findByUuid(anyString())).thenReturn(Optional.of(personEntity));
        when(mapper.toRequest(any(PersonEntity.class))).thenReturn(personRequestDTO);
        when(addressService.update(anyMap(), any(PersonEntity.class))).thenReturn(null);

        service.update("9209786f-c1ed-4014-a3fe-5601aacee291", changes);

        verify(repository, times(1)).findByUuid("9209786f-c1ed-4014-a3fe-5601aacee291");
        verify(mapper, times(1)).toRequest(personEntityArgumentCaptor.capture());
        verify(repository, times(1)).save(personEntityArgumentCaptor.capture());
        verify(addressService, times(1)).update(eq(null), personEntityArgumentCaptor.capture());

        PersonEntity entityUpdated = personEntityArgumentCaptor.getValue();

        verify(mapper, times(1)).toResponse(personEntityArgumentCaptor.capture(), eq(null), eq(null), eq(null));

        assertEquals("josefino", entityUpdated.getFullName());
        assertEquals("roberto", entityUpdated.getFathersName());
        assertEquals("jessica", entityUpdated.getMothersName());
        assertEquals(SexType.M, entityUpdated.getSexType());
        assertEquals(LocalDate.parse("1997-08-27"), entityUpdated.getBirthDate());
        assertEquals("alguem@foo.com.br", entityUpdated.getEmail());
        assertEquals("879.987.987-99", entityUpdated.getDocumentNumber());


    }


    @Test
    void updateWithEmailAndDocumentNumberAlReadyRegistered() {


        ArgumentCaptor<PersonFilter> personFilter = ArgumentCaptor.forClass(PersonFilter.class);


        ArgumentCaptor<PersonEntity> personEntityArgumentCaptor = ArgumentCaptor.forClass(PersonEntity.class);

        PersonEntity personEntity = spy(PersonEntity.class);

        personEntity.setFullName("Alguem da silva");
        personEntity.setFathersName("Brincante");
        personEntity.setMothersName("Roselia");
        personEntity.setSexType(SexType.M);
        personEntity.setBirthDate(LocalDate.parse("1997-08-27"));
        personEntity.setEmail("alguem@foo.com.br");
        personEntity.setDocumentNumber("879.987.987-99");

        PersonRequestDTO personRequestDTO = spy(PersonRequestDTO.class);

        personRequestDTO.setFullName("josefino");
        personRequestDTO.setFathersName("josefino");
        personRequestDTO.setMothersName("jessica");
        personRequestDTO.setSexType(SexType.F);
        personRequestDTO.setBirthDate(LocalDate.parse("2007-08-27"));
        personRequestDTO.setEmail("roberto@foo.com.br");
        personRequestDTO.setDocumentNumber("734.602.110-08");

        LinkedHashMap<String, Object> changesAddress = new LinkedHashMap<>();

        changesAddress.put("zipCode", "73080-100");
        changesAddress.put("street", "QMS 55A");
        changesAddress.put("neighborhood", "Setor de Mansões de Sobradinho");
        changesAddress.put("city", "Brasília");
        changesAddress.put("state", "DF");
        changesAddress.put("complement", "La manos");


        AddressDTO addressDTOResponseDTO = spy(AddressDTO.class);


        Map<String, Object> changes = new HashMap<>();

        changes.put("fullName", "josefino");
        changes.put("mothersName", "jessica");
        changes.put("fathersName", "roberto");
        changes.put("sexType", "F");
        changes.put("birthDate", "2007-08-27");
        changes.put("email", "roberto@foo.com.br");
        changes.put("documentNumber", "734.602.110-08");
        changes.put("address", changesAddress);


        when(repository.findByUuid(anyString())).thenReturn(Optional.of(personEntity));
        when(mapper.toRequest(any(PersonEntity.class))).thenReturn(personRequestDTO);
        when(addressService.update(anyMap(), any(PersonEntity.class))).thenReturn(addressDTOResponseDTO);

        when(repository.findAllAndFilter(any(PersonFilter.class), any(Pageable.class))).thenReturn(Collections.singletonList(new PersonEntity()));


        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> service.update("9209786f-c1ed-4014-a3fe-5601aacee291", changes));

        List<String> messages = abstractRuntimeException.getMessages();
        assertFalse(messages.isEmpty());
        assertEquals(2, messages.size());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, abstractRuntimeException.getHttpStatus());
        assertEquals(getMessage(MessageErrorImpl.RESOURCE_ALREADY_REGISTERED, getMessage(MessageErrorImpl.DOCUMENT_NUMBER, "734.602.110-08")), messages.get(0));
        assertEquals(getMessage(MessageErrorImpl.RESOURCE_ALREADY_REGISTERED, getMessage(MessageErrorImpl.EMAIL, "roberto@foo.com.br")), messages.get(1));


        verify(repository, times(2)).findAllAndFilter(personFilter.capture(), eq(Pageable.unpaged()));
        verify(repository, times(1)).findByUuid("9209786f-c1ed-4014-a3fe-5601aacee291");
        verify(mapper, times(1)).toRequest(personEntityArgumentCaptor.capture());
        verify(repository, never()).save(personEntityArgumentCaptor.capture());
        verify(addressService, never()).update(eq(changesAddress), personEntityArgumentCaptor.capture());


    }


    @Test
    void updateWithInvalidFullNameAndDocumentNumber() {

        ArgumentCaptor<PersonEntity> personEntityArgumentCaptor = ArgumentCaptor.forClass(PersonEntity.class);


        PersonEntity personEntity = spy(PersonEntity.class);

        personEntity.setFullName("Alguem da silva");
        personEntity.setFathersName("Brincante");
        personEntity.setMothersName("Roselia");
        personEntity.setSexType(SexType.M);
        personEntity.setBirthDate(LocalDate.parse("1997-08-27"));
        personEntity.setEmail("alguem@foo.com.br");
        personEntity.setDocumentNumber("879.987.987-99");

        PersonRequestDTO personRequestDTO = spy(PersonRequestDTO.class);

        personRequestDTO.setFullName("josefino silveira ribeiro alves de oliveira pinhero da silva vieira lima souza da luz");
        personRequestDTO.setFathersName("josefino");
        personRequestDTO.setMothersName("jessica");
        personRequestDTO.setSexType(SexType.F);
        personRequestDTO.setBirthDate(LocalDate.parse("2007-08-27"));
        personRequestDTO.setEmail("roberto@foo.com.br");
        personRequestDTO.setDocumentNumber("555-555-555-55");

        LinkedHashMap<String, Object> changesAddress = new LinkedHashMap<>();

        changesAddress.put("zipCode", "73080-100321321");
        changesAddress.put("street", "QMS 55A");
        changesAddress.put("neighborhood", "Setor de Mansões de Sobradinho");
        changesAddress.put("city", "Brasília");
        changesAddress.put("state", "DF");
        changesAddress.put("complement", "La manos");


        Map<String, Object> changes = new HashMap<>();

        changes.put("fullName", "josefino silveira ribeiro alves de oliveira pinhero da silva vieira lima souza da luz");
        changes.put("mothersName", "jessica");
        changes.put("fathersName", "roberto");
        changes.put("sexType", "F");
        changes.put("birthDate", "2007-08-27");
        changes.put("email", "roberto@foo.com.br");
        changes.put("documentNumber", "555-555-555-55");
        changes.put("address", changesAddress);

        AddressDTO addressDTOResponseDTO = spy(AddressDTO.class);


        when(repository.findByUuid(anyString())).thenReturn(Optional.of(personEntity));
        when(mapper.toRequest(any(PersonEntity.class))).thenReturn(personRequestDTO);
        when(addressService.update(anyMap(), any(PersonEntity.class))).thenReturn(addressDTOResponseDTO);


        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> service.update("9209786f-c1ed-4014-a3fe-5601aacee291", changes));

        List<String> messages = abstractRuntimeException.getMessages();

        assertFalse(messages.isEmpty());
        assertEquals(2, messages.size());

        assertTrue(messages.get(0).contains("fullName"));
        assertEquals(getMessage(MessageErrorImpl.INVALID_DOCUMENT_NUMBER, "555-555-555-55"), messages.get(1));


        verify(repository, times(1)).findByUuid("9209786f-c1ed-4014-a3fe-5601aacee291");
        verify(mapper, times(1)).toRequest(personEntityArgumentCaptor.capture());
        verify(addressService, times(1)).updateAndValidateObject(eq(changesAddress), personEntityArgumentCaptor.capture());
        verify(repository, never()).save(any());
        verify(mapper, never()).toResponse(any(), any(), any(), any());


    }


    @Test
    void updateWithInvalidFullNameAndZipCode() {

        ArgumentCaptor<PersonEntity> personEntityArgumentCaptor = ArgumentCaptor.forClass(PersonEntity.class);


        PersonEntity personEntity = spy(PersonEntity.class);

        personEntity.setFullName("Alguem da silva");
        personEntity.setFathersName("Brincante");
        personEntity.setMothersName("Roselia");
        personEntity.setSexType(SexType.M);
        personEntity.setBirthDate(LocalDate.parse("1997-08-27"));
        personEntity.setEmail("alguem@foo.com.br");
        personEntity.setDocumentNumber("879.987.987-99");

        PersonRequestDTO personRequestDTO = spy(PersonRequestDTO.class);

        personRequestDTO.setDocumentNumber("879.987.987-99");
        personRequestDTO.setFullName("josefino silveira ribeiro alves de oliveira pinhero da silva vieira lima souza da luz");
        personRequestDTO.setFathersName("josefino");
        personRequestDTO.setMothersName("jessica");
        personRequestDTO.setSexType(SexType.F);
        personRequestDTO.setBirthDate(LocalDate.parse("2007-08-27"));
        personRequestDTO.setEmail("roberto@foo.com.br");

        LinkedHashMap<String, Object> changesAddress = new LinkedHashMap<>();

        changesAddress.put("zipCode", "73080-100321321");
        changesAddress.put("street", "QMS 55A");
        changesAddress.put("neighborhood", "Setor de Mansões de Sobradinho");
        changesAddress.put("city", "Brasília");
        changesAddress.put("state", "DF");
        changesAddress.put("complement", "La manos");


        Map<String, Object> changes = new HashMap<>();

        changes.put("fullName", "josefino silveira ribeiro alves de oliveira pinhero da silva vieira lima souza da luz");
        changes.put("mothersName", "jessica");
        changes.put("fathersName", "roberto");
        changes.put("sexType", "F");
        changes.put("birthDate", "2007-08-27");
        changes.put("email", "roberto@foo.com.br");
        changes.put("address", changesAddress);

        when(repository.findByUuid(anyString())).thenReturn(Optional.of(personEntity));
        when(mapper.toRequest(any(PersonEntity.class))).thenReturn(personRequestDTO);
        when(addressService.updateAndValidateObject(anyMap(), any(PersonEntity.class))).thenThrow(new AbstractRuntimeException(Collections.singletonList("zipCode size must be between 8 and 11!"), HttpStatus.BAD_REQUEST));


        AbstractRuntimeException abstractRuntimeException = assertThrows(AbstractRuntimeException.class, () -> service.update("9209786f-c1ed-4014-a3fe-5601aacee291", changes));

        List<String> messages = abstractRuntimeException.getMessages();

        assertFalse(messages.isEmpty());
        assertEquals(2, messages.size());

        assertTrue(messages.get(0).contains("fullName"));
        assertEquals("zipCode size must be between 8 and 11!", messages.get(1));


        verify(repository, times(1)).findByUuid("9209786f-c1ed-4014-a3fe-5601aacee291");
        verify(mapper, times(1)).toRequest(personEntityArgumentCaptor.capture());
        verify(addressService, times(1)).updateAndValidateObject(eq(changesAddress), personEntityArgumentCaptor.capture());
        verify(repository, never()).save(any());
        verify(mapper, never()).toResponse(any(), any(), any(), any());

    }


}
