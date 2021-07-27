package com.person.api.model.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.person.api.model.enums.SexType;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class PersonFilter {

    private Long id;
    private String fullName;
    private String documentNumber;
    private String fathersName;
    private String mothersName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String initBirthDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String endBirthDate;
    private SexType sexType;
    private String email;
    private String zipCode;
    private String city;
    private String state;

    public PersonFilter() {
    }

    public PersonFilter(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public LocalDate getInitBirthDate() {
        return StringUtils.isNotBlank(initBirthDate) ? LocalDate.parse(initBirthDate) : null;
    }

    public void setInitBirthDate(String initBirthDate) {
        this.initBirthDate = initBirthDate;
    }

    public LocalDate getEndBirthDate() {

        return StringUtils.isNotBlank(endBirthDate) ? LocalDate.parse(endBirthDate) : null;

    }

    public void setEndBirthDate(String endBirthDate) {
        this.endBirthDate = endBirthDate;
    }

    public SexType getSexType() {
        return sexType;
    }

    public void setSexType(SexType sexType) {
        this.sexType = sexType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
