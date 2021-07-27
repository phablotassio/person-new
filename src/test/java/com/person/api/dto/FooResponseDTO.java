package com.person.api.dto;

public class FooResponseDTO extends FooRequestDTO {

    public FooResponseDTO() {
    }

    public FooResponseDTO(FooRequestDTO fooRequestDTO) {
        this.setName(fooRequestDTO.getName());
        this.setId(fooRequestDTO.getId());
    }
}
