package com.person.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class FooRequestDTO {

    private Long id;

    @NotBlank
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fooDate;

    private List<BarDTO> dtoList;

    private BarDTO barDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<BarDTO> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<BarDTO> dtoList) {
        this.dtoList = dtoList;
    }

    public BarDTO getBarDTO() {
        return barDTO;
    }

    public void setBarDTO(BarDTO barDTO) {
        this.barDTO = barDTO;
    }

    public Date getFooDate() {
        return fooDate;
    }

    public void setFooDate(Date fooDate) {
        this.fooDate = fooDate;
    }
}