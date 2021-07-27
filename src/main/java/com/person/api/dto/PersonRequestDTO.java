package com.person.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.person.api.client.dto.CredentialDTO;
import com.person.api.model.enums.SexType;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class PersonRequestDTO {

    @NotBlank
    @Size(min = 3, max = 60)
    private String fullName;

    @NotBlank
    @Size(min = 11, max = 14)
    private String documentNumber;

    @Size(min = 3, max = 60)
    private String fathersName;

    @NotBlank
    @Size(min = 3, max = 60)
    private String mothersName;

    @NotNull
    private SexType sexType;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank
    @Size(min = 3, max = 70)
    private String email;

    @Valid
    private AddressDTO address;

    @Valid
    private  TelephoneDTO phone;

    @Valid
    private HeightDTO height;

    @Valid
    private WeightDTO weight;

    @Valid
    private CredentialDTO credential;

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

    public SexType getSexType() {
        return sexType;
    }

    public void setSexType(SexType sexType) {
        this.sexType = sexType;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public TelephoneDTO getPhone() {
        return phone;
    }

    public void setPhone(TelephoneDTO phone) {
        this.phone = phone;
    }

    public HeightDTO getHeight() {
        return height;
    }

    public void setHeight(HeightDTO height) {
        this.height = height;
    }

    public WeightDTO getWeight() {
        return weight;
    }

    public void setWeight(WeightDTO weight) {
        this.weight = weight;
    }

    public CredentialDTO getCredential() {
        return credential;
    }

    public void setCredential(CredentialDTO credential) {
        this.credential = credential;
    }
}
