package com.person.api.controller;

import com.person.api.dto.PersonRequestDTO;
import com.person.api.dto.PersonResponseDTO;
import com.person.api.model.filter.PersonFilter;
import com.person.api.service.PersonService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public PersonResponseDTO insert(@RequestBody @Valid PersonRequestDTO personRequestDTO) {

        return personService.createPerson(personRequestDTO);
    }

    @GetMapping
    public List<PersonResponseDTO> listPerson(PersonFilter personFilter, Pageable pageable) {
        return personService.getPersons(personFilter, pageable);
    }

    @GetMapping("/{uuid}")
    public PersonResponseDTO getUser(@PathVariable String uuid) {
        return personService.getPerson(uuid);
    }

    @PatchMapping("/{uuid}")
    public PersonResponseDTO updatePerson(@PathVariable String uuid, @Valid @RequestBody Map<String, Object> fields) {

        return personService.update(uuid, fields);
    }
}

