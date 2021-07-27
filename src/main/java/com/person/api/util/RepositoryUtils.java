package com.person.api.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class RepositoryUtils {

    private RepositoryUtils() {
    }

    public static void addPagination(TypedQuery<?> query, Pageable pageable) {

        if (pageable.isPaged()) {
            int actualPage = pageable.getPageNumber();
            int totalRegistersPerPage = pageable.getPageSize();
            int firstRegisterOfPage = actualPage * totalRegistersPerPage;

            query.setFirstResult(firstRegisterOfPage);
            query.setMaxResults(totalRegistersPerPage);
        }

    }

    public static void addOrdination(Pageable pageable, CriteriaBuilder builder, CriteriaQuery<?> criteria, Root<?> root) {

        Sort sort = pageable.getSort();

        if (sort.isSorted()) {
            Sort.Order order = sort.iterator().next();
            String field = order.getProperty();
            criteria.orderBy(order.isAscending() ? builder.asc(root.get(field)) : builder.desc(root.get(field)));
        }

    }

    public static boolean predicatesExists(Predicate[] predicates) {

        return predicates != null && predicates.length > 0;
    }
}
