package com.person.api.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TB_WEIGHT_HEIGHT", schema = "DBPERSON")
public class WeighAndHeightEntity extends SimpleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_WEIGHT_HEIGHT")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PERSON", foreignKey = @ForeignKey(name = "FK_PERSON_WEIGHT"), nullable = false)
    private PersonEntity personEntity;

    @Column(name = "WEIGHT", nullable = false)
    private String weight;

    @Column(name = "HEIGHT", nullable = false, length = 6)
    private String height;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public PersonEntity getPersonEntity() {
        return personEntity;
    }

    public void setPersonEntity(PersonEntity personEntity) {
        this.personEntity = personEntity;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeighAndHeightEntity weight = (WeighAndHeightEntity) o;
        return Objects.equals(id, weight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
