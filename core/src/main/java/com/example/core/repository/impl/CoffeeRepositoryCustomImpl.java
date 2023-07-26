package com.example.core.repository.impl;

import com.example.core.repository.CoffeeRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CoffeeRepositoryCustomImpl implements CoffeeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long getSequenceDb(String sequenceName) {
        Query query = em.createNativeQuery(String.format("SELECT nextval('%s')  from dual", sequenceName));
        return ((Number) query.getSingleResult()).longValue();
    }
}
