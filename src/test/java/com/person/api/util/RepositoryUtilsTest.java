package com.person.api.util;

import com.person.api.model.FooEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryUtilsTest {

    @Autowired
    private EntityManager entityManager;

    private Pageable pageable;

    @BeforeEach
    void initializingVariables() {
        pageable = mock(Pageable.class);
    }

    @Test
    void addPaginationInPageablePaged() {

        int maxResults = 5;
        int pageNumber = 1;
        int firstResult = maxResults / pageNumber;

        when(pageable.isPaged()).thenReturn(Boolean.TRUE);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FooEntity> criteriaQuery = builder.createQuery(FooEntity.class);
        Root<FooEntity> root = criteriaQuery.from(FooEntity.class);
        TypedQuery<FooEntity> typedQuery = entityManager.createQuery(criteriaQuery);

        when(pageable.getPageSize()).thenReturn(maxResults);
        when(pageable.getPageNumber()).thenReturn(pageNumber);

        RepositoryUtils.addPagination(typedQuery, pageable);
        assertEquals(maxResults, typedQuery.getMaxResults());
        assertEquals(firstResult, typedQuery.getFirstResult());

    }


    @Test
    void addPaginationInPageableUnPaged() {

        int maxResults = 5;
        int pageNumber = 1;
        int firstResult = maxResults / pageNumber;

        when(pageable.isPaged()).thenReturn(Boolean.TRUE);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FooEntity> criteriaQuery = builder.createQuery(FooEntity.class);
        Root<FooEntity> root = criteriaQuery.from(FooEntity.class);
        TypedQuery<FooEntity> typedQuery = entityManager.createQuery(criteriaQuery);

        when(pageable.getPageSize()).thenReturn(maxResults);
        when(pageable.getPageNumber()).thenReturn(pageNumber);

        RepositoryUtils.addPagination(typedQuery, pageable);
        assertEquals(maxResults, typedQuery.getMaxResults());
        assertEquals(firstResult, typedQuery.getFirstResult());

    }

    @Test
    void addOrdination() {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FooEntity> criteriaQuery = builder.createQuery(FooEntity.class);
        Root<FooEntity> root = criteriaQuery.from(FooEntity.class);
        when(pageable.getSort()).thenReturn(Sort.by(Sort.Order.asc("id")));

        RepositoryUtils.addOrdination(pageable, builder, criteriaQuery, root);
    }

    @Test
    void addOrdinationDesc() {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FooEntity> criteriaQuery = builder.createQuery(FooEntity.class);
        Root<FooEntity> root = criteriaQuery.from(FooEntity.class);
        when(pageable.getSort()).thenReturn(Sort.by(Sort.Order.desc("id")));
        RepositoryUtils.addOrdination(pageable, builder, criteriaQuery, root);
    }

    @Test
    void dontAddOrdination() {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FooEntity> criteriaQuery = builder.createQuery(FooEntity.class);
        Root<FooEntity> root = criteriaQuery.from(FooEntity.class);
        when(pageable.getSort()).thenReturn(Sort.unsorted());
        RepositoryUtils.addOrdination(pageable, builder, criteriaQuery, root);
    }

    @Test
    void predicatesDontExists() {

        List<Predicate> predicates = new ArrayList<>();
        Boolean result = RepositoryUtils.predicatesExists(new Predicate[predicates.size()]);
        assertFalse(result);
    }

    @Test
    void predicatesExistsWithNullPredicate() {

        assertFalse(RepositoryUtils.predicatesExists(null));
    }


    @Test
    void predicatesExists() {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        List<Predicate> predicates = Collections.singletonList(builder.and());
        Boolean result = RepositoryUtils.predicatesExists(new Predicate[predicates.size()]);
        assertTrue(result);
    }
}