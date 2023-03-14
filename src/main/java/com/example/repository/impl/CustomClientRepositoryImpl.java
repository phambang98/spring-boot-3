package com.example.repository.impl;

import com.example.model.ClientBean;
import com.example.repository.CustomClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class CustomClientRepositoryImpl implements CustomClientRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ClientBean> findAbc() {
        StringBuilder stringBuilder = new StringBuilder("select id,user_name as userName,email,password,Last_update_date as lastUpdateDate from client");
        Query query = em.createNativeQuery(stringBuilder.toString(),"ClientBeanMapping");
        return query.getResultList();
    }

    @Override
    public List<ClientBean> getAllClientBean() {
        Query query = em.createNamedQuery("getAllClientBean",ClientBean.class);
        query.setParameter("userName","2");
        return query.getResultList();
    }


}
