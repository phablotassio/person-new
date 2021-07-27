package com.person.api.model;

import com.person.api.model.enums.SexType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_PERSON", schema = "DBPERSON")
public class PersonEntity extends SimpleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSON")
    private Long id;

    @Column(name = "FULL_NAME", nullable = false, length = 60)
    private String fullName;

    @Column(name = "DOCUMENT_NUMBER", nullable = false, length = 14, unique = true)
    private String documentNumber;

    @Column(name = "DT_BIRTH", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "TP_SEX", length = 1)
    private SexType sexType;

    @Column(name = "NM_FATHER", length = 60)
    private String fathersName;

    @Column(name = "NM_MOTHER", nullable = false, length = 60)
    private String mothersName;

    @Email
    @Column(name = "EMAIL", nullable = false, length = 70, unique = true)
    private String email;

    @OneToMany(mappedBy = "personEntity", fetch = FetchType.LAZY)
    private List<AddressEntity> addresses;

    @OneToMany(mappedBy = "personEntity", fetch = FetchType.LAZY)
    private List<AddressEntity> phones;

    public Long getId() {
        return id;
    }

    public void setId(Long idPerson) {
        this.id = idPerson;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNUmber) {
        this.documentNumber = documentNUmber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public SexType getSexType() {
        return sexType;
    }

    public void setSexType(SexType sexType) {
        this.sexType = sexType;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String nameFather) {
        this.fathersName = nameFather;
    }

    public String getMothersName() {
        return mothersName;
    }

    public void setMothersName(String nameMother) {
        this.mothersName = nameMother;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    public List<AddressEntity> getPhones() {
        return phones;
    }

    public void setPhones(List<AddressEntity> phones) {
        this.phones = phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
