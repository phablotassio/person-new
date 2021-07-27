package com.person.api.repository.person;

import com.person.api.model.AddressEntity;
import com.person.api.model.PersonEntity;
import com.person.api.model.filter.PersonFilter;
import com.person.api.util.DocumentUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.person.api.util.RepositoryUtils.*;

public class PersonRepositoryImpl implements PersonQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PersonEntity> findAllAndFilter(PersonFilter personFilter, Pageable pageable) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PersonEntity> criteriaQuery = builder.createQuery(PersonEntity.class);
        Root<PersonEntity> rootPerson = criteriaQuery.from(PersonEntity.class);
        //Fetch<PersonEntity, AddressEntity> addressesFetch = rootPerson.fetch("addresses", JoinType.LEFT);
        Join<PersonEntity, AddressEntity> addressesJoin = rootPerson.join("addresses", JoinType.LEFT);

        Predicate[] predicates = createPersonPredicates(builder, personFilter, rootPerson, addressesJoin);


        if (predicatesExists(predicates)) {
            criteriaQuery.where(predicates);
        }

        addOrdination(pageable, builder, criteriaQuery, rootPerson);
        TypedQuery<PersonEntity> query = entityManager.createQuery(criteriaQuery);
        addPagination(query, pageable);

        List<PersonEntity> resultList = query.getResultList();

        resultList.forEach(x -> x.getAddresses().forEach(a -> a.setPersonEntity(null)));

        return new PageImpl<>(resultList).getContent();
    }

    private Predicate[] createPersonPredicates(CriteriaBuilder builder, PersonFilter personFilter, Root<PersonEntity> rootPerson, Join<PersonEntity, AddressEntity> addresses) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.isNull(personFilter)) {
            return null;
        }

        if (Objects.nonNull(personFilter.getId())) {
            predicates.add(builder.notEqual(rootPerson.get("id"), personFilter.getId()));
        }

        if (StringUtils.isNotBlank(personFilter.getFullName())) {
            predicates.add(builder.like(builder.lower(rootPerson.get("fullName")), personFilter.getFullName().toLowerCase() + "%"));
        }

        if (StringUtils.isNotBlank(personFilter.getDocumentNumber())) {
            predicates.add(builder.equal(rootPerson.get("documentNumber"), DocumentUtils.unmask(personFilter.getDocumentNumber())));
        }

        if (StringUtils.isNotBlank(personFilter.getFathersName())) {
            predicates.add(builder.like(builder.lower(rootPerson.get("fathersName")), personFilter.getFathersName().toLowerCase() + "%"));
        }

        if (StringUtils.isNotBlank(personFilter.getMothersName())) {
            predicates.add(builder.like(builder.lower(rootPerson.get("mothersName")), personFilter.getMothersName().toLowerCase() + "%"));
        }

        if (Objects.nonNull(personFilter.getInitBirthDate())) {
            predicates.add(builder.greaterThanOrEqualTo(rootPerson.get("birthDate"), personFilter.getInitBirthDate()));
        }

        if (Objects.nonNull(personFilter.getEndBirthDate())) {
            predicates.add(builder.lessThanOrEqualTo(rootPerson.get("birthDate"), personFilter.getEndBirthDate()));
        }

        if (Objects.nonNull(personFilter.getSexType())) {
            predicates.add(builder.equal(rootPerson.get("sexType"), personFilter.getSexType()));
        }

        if (StringUtils.isNotBlank(personFilter.getEmail())) {
            predicates.add(builder.equal(rootPerson.get("email"), personFilter.getEmail()));
        }

        if (Objects.nonNull(personFilter.getState())) {
            predicates.add(builder.like(builder.lower(addresses.get("state")), personFilter.getState().toLowerCase() + "%"));
        }

        if (Objects.nonNull(personFilter.getCity())) {
            predicates.add(builder.like(builder.lower(addresses.get("city")), personFilter.getCity().toLowerCase() + "%"));
        }

        if (Objects.nonNull(personFilter.getZipCode())) {
            predicates.add(builder.equal(addresses.get("zipCode"), personFilter.getZipCode()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

}
