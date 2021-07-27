package com.person.api.model;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "TB_ADDRESS", schema = "DBPERSON")
public class AddressEntity extends SimpleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ADDRESS", columnDefinition = "int")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERSON", foreignKey = @ForeignKey(name = "FK_PERSON_ADDRESS"), nullable = false, unique = true)
    private PersonEntity personEntity;

    @Column(name = "ZIPCODE", nullable = false, length = 13)
    private String zipCode;

    @Column(name = "CITY", nullable = false, length = 40)
    private String city;

    @Column(name = "STREET", nullable = false, length = 70)
    private String street;

    @Column(name = "NEIGHBORHOOD", length = 70)
    private String neighborhood;

    @Column(name = "STATE", nullable = false, length = 30)
    private String state;

    @Column(name = "COMPLEMENT", length = 70)
    private String complement;

    public Long getId() {
        return id;
    }

    public void setId(Long idAddress) {
        this.id = idAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String cep) {
        this.zipCode = cep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String publicPlace) {
        this.street = publicPlace;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
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
        AddressEntity that = (AddressEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
