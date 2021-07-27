package com.person.api.repository;

import com.person.api.dto.WeightAndHeightDTO;
import com.person.api.model.WeighAndHeightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeighAndHeightRepository extends JpaRepository<WeighAndHeightEntity, Long> {

    @Query("select new com.person.api.dto.WeightAndHeightDTO(wh.weight, wh.height) from WeighAndHeightEntity wh join wh.personEntity where wh.personEntity.id = :idPerson and wh in (select max(w.id) from WeighAndHeightEntity w)")
    Optional<WeightAndHeightDTO> getByIdPerson(@Param("idPerson") Long idPerson);
}
