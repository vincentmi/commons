package com.vnzmi.commons.data;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@Transactional(readOnly = true)
public class PhiloRepositoryImpl<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>
        implements PhiloRepository<T,ID>{

    private EntityManager entityManager;

    public PhiloRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        entityManager = em;
    }

    public PhiloRepositoryImpl(JpaEntityInformation jpaEntityInformation,EntityManager entityManager)
    {
        super(jpaEntityInformation,entityManager);
        this.entityManager = entityManager;
    }

    public List<T> fetchPage()
    {
        return fetchPage(null);
    }

    public List<T>  fetchPage(@Nullable Specification<T> spec)
    {

//        CriteriaBuilder  cb =  entityManager.getCriteriaBuilder();
//        CriteriaQuery<T> criteriaQuery = cb.createQuery(domainClass);
//        criteriaQuery.from(domainClass);
//        TypedQuery<T> typedQuery  =  entityManager.createQuery(criteriaQuery);
//        return typedQuery.getResultList();
        return null;
    }

}
