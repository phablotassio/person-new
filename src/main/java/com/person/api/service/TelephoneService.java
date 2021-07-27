package com.person.api.service;

import com.person.api.dto.TelephoneDTO;
import com.person.api.dto.TelephoneQueryDTO;
import com.person.api.exception.AbstractRuntimeException;
import com.person.api.mapper.TelephoneMapper;
import com.person.api.model.PersonEntity;
import com.person.api.repository.TelephoneRepository;
import com.person.api.util.ObjectUtils;
import com.person.api.util.ValidateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TelephoneService {

    private final TelephoneMapper mapper;

    private final TelephoneRepository repository;


    public TelephoneService(TelephoneMapper mapper, TelephoneRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public List<TelephoneDTO> getPhones() {

        return repository.findAll().stream().map(mapper::toPhoneDTO).collect(Collectors.toList());
    }

    public TelephoneDTO getPhoneDTO(Long idPerson) {

        return repository.getByIdPerson(idPerson).orElse(null);

    }

    @Transactional
    public TelephoneDTO cratePhone(TelephoneDTO telephoneDTO, PersonEntity personEntity) {

        if (Objects.isNull(telephoneDTO)) {
            return null;
        }

        return mapper.toPhoneDTO(repository.save(mapper.toPhoneEntity(telephoneDTO, personEntity)));
    }


    @Transactional
    TelephoneDTO update(Map<String, Object> fields, @NonNull PersonEntity personEntity) {

        if (Objects.isNull(fields)) {
            return getPhoneDTO(personEntity.getId());
        }

        TelephoneQueryDTO phoneUpdated = updateAndValidateObject(fields, personEntity);

        repository.save(mapper.toPhoneEntity(phoneUpdated, personEntity));

        phoneUpdated.setId(null);

        return phoneUpdated;
    }


    TelephoneQueryDTO updateAndValidateObject(Map<String, Object> fields, PersonEntity personEntity) {

        TelephoneQueryDTO addressDTO = getTelephoneQueryDTO(personEntity.getId());

        addressDTO = addressDTO != null ? addressDTO : new TelephoneQueryDTO();

        TelephoneQueryDTO addressUpdated = ObjectUtils.patch(fields, addressDTO);

        List<String> errors = ValidateUtils.validateObject(addressUpdated);

        if (!errors.isEmpty()) {
            throw new AbstractRuntimeException(errors, HttpStatus.BAD_REQUEST);
        }

        return addressUpdated;
    }

    private TelephoneQueryDTO getTelephoneQueryDTO(Long idPerson) {

        return repository.getByIdPerson(idPerson).orElse(null);
    }

}
