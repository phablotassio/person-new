package com.person.api.service;

import com.person.api.dto.AddressDTO;
import com.person.api.dto.AddressQueryDTO;
import com.person.api.exception.AbstractRuntimeException;
import com.person.api.mapper.AddressMapper;
import com.person.api.model.AddressEntity;
import com.person.api.model.PersonEntity;
import com.person.api.repository.AddressRepository;
import com.person.api.util.ObjectUtils;
import com.person.api.util.ValidateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AddressService {

    private final AddressMapper mapper;

    private final AddressRepository repository;

    public AddressService(AddressMapper mapper, AddressRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional
    AddressDTO processAddress(AddressDTO addressDTO, PersonEntity personEntity) {

        if (Objects.isNull(addressDTO)) {
            return null;
        }

        AddressEntity addressEntity = repository.save(mapper.toAddressEntity(addressDTO, personEntity));

        return mapper.toAddressDTO(addressEntity);
    }

    AddressDTO update(Map<String, Object> fields, @NonNull PersonEntity personEntity) {

        if (Objects.isNull(fields)) {
            return getAddressDTO(personEntity.getId());
        }

        AddressQueryDTO addressUpdated = updateAndValidateObject(fields, personEntity);

        repository.save(mapper.toAddressEntity(addressUpdated, personEntity));

        addressUpdated.setId(null);

        return addressUpdated;
    }

    AddressQueryDTO updateAndValidateObject(Map<String, Object> fields, PersonEntity personEntity) {

        AddressQueryDTO addressDTO = getAddressQueryDTO(personEntity.getId());

        addressDTO = addressDTO != null ? addressDTO : new AddressQueryDTO();

        AddressQueryDTO addressUpdated = ObjectUtils.patch(fields, addressDTO);

        List<String> errors = ValidateUtils.validateObject(addressUpdated);

        if (!errors.isEmpty()) {
            throw new AbstractRuntimeException(errors, HttpStatus.BAD_REQUEST);
        }

        return addressUpdated;
    }

    AddressDTO getAddressDTO(Long idPerson) {

        return repository.getByIdPerson(idPerson).orElse(null);
    }

    private AddressQueryDTO getAddressQueryDTO(Long idPerson) {

        return repository.getByIdPerson(idPerson).orElse(null);
    }
}
