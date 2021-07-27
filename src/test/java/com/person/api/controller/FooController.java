package com.person.api.controller;

import com.person.api.client.FooClient;
import com.person.api.dto.FooRequestDTO;
import com.person.api.dto.FooResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/test")
public class FooController {

    @Autowired
    private FooClient fooClient;

    @PostMapping
    public FooResponseDTO insert(@RequestBody @Valid FooRequestDTO fooRequestDTO) {

        return new FooResponseDTO(fooRequestDTO);
    }

    @PostMapping("/foo")
    public FooResponseDTO callClient(@RequestBody @Valid FooRequestDTO fooRequestDTO) {

        return fooClient.insert(fooRequestDTO);
    }


}
