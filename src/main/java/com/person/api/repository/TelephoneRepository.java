package com.person.api.repository;

import com.person.api.dto.TelephoneQueryDTO;
import com.person.api.model.TelephoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelephoneRepository extends JpaRepository<TelephoneEntity, Long> {

    @Query("select new com.person.api.dto.TelephoneQueryDTO(ph.number, ph.areaCode, ph.id) from TelephoneEntity ph join ph.personEntity where ph.personEntity.id = :idPerson")
    Optional<TelephoneQueryDTO> getByIdPerson(@Param("idPerson") Long idPerson);
}
