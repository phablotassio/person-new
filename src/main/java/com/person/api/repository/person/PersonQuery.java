package com.person.api.repository.person;

import com.person.api.model.PersonEntity;
import com.person.api.model.filter.PersonFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonQuery {

    List<PersonEntity> findAllAndFilter(PersonFilter personFilter, Pageable pageable);
}
