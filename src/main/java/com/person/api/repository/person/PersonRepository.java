package com.person.api.repository.person;

import com.person.api.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity,Long>, PersonQuery {

    @Query(value = "select vaccine from PersonEntity vaccine where vaccine.uuid = :uuid")
    Optional<PersonEntity> findByUuid(@Param("uuid") String uuid);
	
}
