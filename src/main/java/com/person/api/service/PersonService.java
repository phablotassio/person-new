package com.person.api.service;

import com.person.api.dto.*;
import com.person.api.exception.AbstractRuntimeException;
import com.person.api.exception.MessageErrorImpl;
import com.person.api.mapper.PersonMapper;
import com.person.api.model.PersonEntity;
import com.person.api.model.filter.PersonFilter;
import com.person.api.repository.person.PersonRepository;
import com.person.api.util.DocumentUtils;
import com.person.api.util.ObjectUtils;
import com.person.api.util.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.person.api.util.MessagesUtil.getMessage;

@Service
public class PersonService {

    public static final String ADDRESS_FIELD = "address";
    public static final String PHONE_FIELD = "phone";
    public static final String DOCUMENT_NUMBER_FIELD = "documentNumber";
    public static final String EMAIL_FIELD = "email";
    public static final String WEIGHT_FIELD = "weight";
    public static final String HEIGHT_FIELD = "height";
    public static final String BIRTH_DATE_FIELD = "birthDate";
    private final PersonRepository personRepository;
    private final PersonMapper mapper;
    private final AddressService addressService;
    private final TelephoneService telephoneService;
    private final WeighAndHeightService weighAndHeightService;
    private final AuthService authService;

    public PersonService(PersonRepository personRepository, PersonMapper mapper, AddressService addressService, TelephoneService telephoneService, WeighAndHeightService weighAndHeightService, AuthService authService) {
        this.personRepository = personRepository;
        this.mapper = mapper;
        this.addressService = addressService;
        this.telephoneService = telephoneService;
        this.weighAndHeightService = weighAndHeightService;
        this.authService = authService;
    }

    public PersonResponseDTO createPerson(PersonRequestDTO personRequestDTO) {

        if (!DocumentUtils.isValidDocumentNumber(personRequestDTO.getDocumentNumber())) {
            throw new AbstractRuntimeException(HttpStatus.BAD_REQUEST, MessageErrorImpl.INVALID_DOCUMENT_NUMBER, personRequestDTO.getDocumentNumber());
        }

        if (personRequestDTO.getBirthDate().isAfter(LocalDate.now())) {
            throw new AbstractRuntimeException(HttpStatus.UNPROCESSABLE_ENTITY, MessageErrorImpl.DATE_MUST_BE_PAST, BIRTH_DATE_FIELD);
        }


        List<String> errors = validateIfDocumentNumberAlreadyRegistered(personRequestDTO.getDocumentNumber());

        errors.addAll(validateIfEmailRegistered(personRequestDTO.getEmail()));


        if (!errors.isEmpty()) {
            throw new AbstractRuntimeException(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        try {
            authService.createUser(personRequestDTO);
        } catch (AbstractRuntimeException abs) {

            if (abs.getHttpStatus().equals(HttpStatus.CONFLICT)) {
                errors.add(getMessage(MessageErrorImpl.RESOURCE_ALREADY_REGISTERED, getMessage(MessageErrorImpl.EMAIL, personRequestDTO.getEmail())));
            } else {
                throw new AbstractRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        if (!errors.isEmpty()) {
            throw new AbstractRuntimeException(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        personRequestDTO.setDocumentNumber(DocumentUtils.unmask(personRequestDTO.getDocumentNumber()));

        PersonEntity personEntity = personRepository.save(mapper.toEntity(personRequestDTO));

        return toPersonResponse(personEntity, addressService.processAddress(personRequestDTO.getAddress(), personEntity), telephoneService.cratePhone(personRequestDTO.getPhone(), personEntity), weighAndHeightService.createWeightAnd(new WeightAndHeightDTO(personRequestDTO.getWeight(), personRequestDTO.getHeight()), personEntity));

    }

    public PersonResponseDTO getPerson(String uuid) {

        PersonEntity personEntity = getPersonEntity(uuid);

        return toPersonResponse(personEntity, addressService.getAddressDTO(personEntity.getId()), telephoneService.getPhoneDTO(personEntity.getId()), weighAndHeightService.getWeight(personEntity.getId()));
    }

    public List<PersonResponseDTO> getPersons(PersonFilter personFilter, Pageable pageable) {

        return personRepository.findAllAndFilter(personFilter, pageable).stream().map(x -> toPersonResponse(x, addressService.getAddressDTO(x.getId()), telephoneService.getPhoneDTO(x.getId()), weighAndHeightService.getWeight(x.getId()))).collect(Collectors.toList());
    }


    public PersonResponseDTO update(String uuid, Map<String, Object> fields) {

        PersonEntity personEntity = getPersonEntity(uuid);

        PersonEntity personEntityUpdated = ObjectUtils.patch(fields, personEntity);

        List<String> errors = ValidateUtils.validateObject(mapper.toRequest(personEntityUpdated));

        String documentNumber = ObjectUtils.getFieldValue(fields, DOCUMENT_NUMBER_FIELD, String.class);

        String email = ObjectUtils.getFieldValue(fields, EMAIL_FIELD, String.class);

        LinkedHashMap<String, Object> height = ObjectUtils.getFieldValue(fields, HEIGHT_FIELD, LinkedHashMap.class);

        LinkedHashMap<String, Object> weight = ObjectUtils.getFieldValue(fields, WEIGHT_FIELD, LinkedHashMap.class);

        if (Objects.nonNull(height) && Objects.nonNull(weight)) {
            errors.addAll(validateWeightAndHeight(height, weight));
        }

        LinkedHashMap<String, Object> address = ObjectUtils.getFieldValue(fields, ADDRESS_FIELD, LinkedHashMap.class);

        LinkedHashMap<String, Object> phone = ObjectUtils.getFieldValue(fields, PHONE_FIELD, LinkedHashMap.class);

        if (StringUtils.isNotBlank(documentNumber)) {

            if (!DocumentUtils.isValidDocumentNumber(documentNumber)) {
                errors.add(getMessage(MessageErrorImpl.INVALID_DOCUMENT_NUMBER, documentNumber));
            }

            errors.addAll(validateIfDocumentNumberAlreadyRegistered(documentNumber, personEntity.getId()));


        }

        if (StringUtils.isNotBlank(email)) {

            errors.addAll(validateIfEmailRegistered(email, personEntity.getId()));
        }

        if (Objects.nonNull(address) && !errors.isEmpty()) {
            try {
                addressService.updateAndValidateObject(address, personEntity);
            } catch (AbstractRuntimeException ex) {
                errors.addAll(ex.getMessages());
            }
        }

        final TelephoneDTO telephoneDTO = updatePhone(personEntityUpdated, errors, phone);

        if (!errors.isEmpty()) {

            throw new AbstractRuntimeException(errors, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return toPersonResponse(personRepository.save(personEntityUpdated), addressService.update(address, personEntityUpdated), telephoneDTO, getWeightDTO(weight, height, personEntityUpdated));

    }

    private TelephoneDTO updatePhone(PersonEntity personEntityUpdated, List<String> errors, LinkedHashMap<String, Object> phone) {

        try {
            return telephoneService.update(phone, personEntityUpdated);
        } catch (AbstractRuntimeException ex) {
            errors.addAll(ex.getMessages());
            return null;
        }
    }

    private List<String> validateWeightAndHeight(LinkedHashMap<String, Object> height, LinkedHashMap<String, Object> weight) {

        List<String> errors = new ArrayList<>();

        if (Objects.isNull(height) || StringUtils.isBlank(ObjectUtils.getFieldValue(height, HEIGHT_FIELD, String.class))) {

            errors.add(getMessage("javax.validation.constraints.NotBlank.message", HEIGHT_FIELD));
        }

        if (Objects.isNull(weight) || StringUtils.isBlank(ObjectUtils.getFieldValue(weight, WEIGHT_FIELD, String.class))) {

            errors.add(getMessage("javax.validation.constraints.NotBlank.message", WEIGHT_FIELD));
        }

        return errors;
    }

    private PersonEntity getPersonEntity(String uuid) {
        return personRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException(getMessage(MessageErrorImpl.RESOURCE_NOT_FOUND, getMessage(MessageErrorImpl.PERSON))));
    }

    private PersonResponseDTO toPersonResponse(PersonEntity personEntity, AddressDTO addressDTO, TelephoneDTO telephoneDTO, WeightAndHeightDTO weightAndHeightDTO) {

        return mapper.toResponse(personEntity, addressDTO, telephoneDTO, weightAndHeightDTO);
    }


    private List<String> validateIfDocumentNumberAlreadyRegistered(String documentNumber) {

        return validateIfDocumentNumberAlreadyRegistered(documentNumber, null);
    }

    private List<String> validateIfDocumentNumberAlreadyRegistered(String documentNumber, Long idPerson) {


        PersonFilter personFilter = new PersonFilter(idPerson);
        personFilter.setDocumentNumber(documentNumber);

        List<String> errors = new ArrayList<>();

        personRepository.findAllAndFilter(personFilter, Pageable.unpaged()).stream().findAny()
                .ifPresent(x ->
                        errors.add(getMessage(MessageErrorImpl.RESOURCE_ALREADY_REGISTERED, getMessage(MessageErrorImpl.DOCUMENT_NUMBER, documentNumber))));

        return errors;
    }


    private List<String> validateIfEmailRegistered(String email) {

        return validateIfEmailRegistered(email, null);
    }

    private List<String> validateIfEmailRegistered(String email, Long idPerson) {

        PersonFilter personFilter = new PersonFilter(idPerson);
        personFilter.setEmail(email);

        List<String> errors = new ArrayList<>();

        personRepository.findAllAndFilter(personFilter, Pageable.unpaged()).stream().findAny()
                .ifPresent(x ->
                        errors.add(getMessage(MessageErrorImpl.RESOURCE_ALREADY_REGISTERED, getMessage(MessageErrorImpl.EMAIL, personFilter.getEmail()))));

        return errors;
    }

    private WeightAndHeightDTO getWeightDTO(LinkedHashMap<String, Object> weight, LinkedHashMap<String, Object> height, PersonEntity personEntity) {


        if (Objects.isNull(height) && Objects.isNull(weight)) {
            return null;
        }

        return weighAndHeightService.createWeightAnd(new WeightAndHeightDTO(ObjectUtils.getFieldValue(weight, WEIGHT_FIELD, String.class), ObjectUtils.getFieldValue(height, HEIGHT_FIELD, String.class)), personEntity);

    }
}
