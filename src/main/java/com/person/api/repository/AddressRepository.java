package com.person.api.repository;

import com.person.api.dto.AddressQueryDTO;
import com.person.api.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    @Query("select new com.person.api.dto.AddressQueryDTO(ad.zipCode, ad.city, ad.street, ad.neighborhood, ad.state, ad.complement, ad.id) from AddressEntity ad join ad.personEntity where ad.personEntity.id = :idPerson")
    Optional<AddressQueryDTO> getByIdPerson(@Param("idPerson") Long idPerson);
}
