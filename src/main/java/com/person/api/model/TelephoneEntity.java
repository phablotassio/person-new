package com.person.api.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TB_PHONE", schema = "DBPERSON")
public class TelephoneEntity extends SimpleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PHONE")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PERSON", foreignKey = @ForeignKey(name = "FK_PERSON_PHONE"), nullable = false, unique = true)
    private PersonEntity personEntity;

    @Column(name = "NUMBER", nullable = false, length = 11)
    private String number;

    @Column(name = "AREA_CODE", length = 2)
    private Long areaCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Long areaCode) {
        this.areaCode = areaCode;
    }

    public PersonEntity getPersonEntity() {
        return personEntity;
    }

    public void setPersonEntity(PersonEntity idPerson) {
        this.personEntity = idPerson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelephoneEntity telephone = (TelephoneEntity) o;
        return id.equals(telephone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
