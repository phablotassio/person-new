package com.person.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TelephoneDTO {


    @NotNull
    private Long areaCode;

    @NotBlank
    private String number;


    public TelephoneDTO(@NotBlank String number, @NotNull Long areaCode) {
        this.number = number;
        this.areaCode = areaCode;
    }

    public TelephoneDTO() {
    }

    public Long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
