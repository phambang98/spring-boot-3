package com.example.springcore.repository.impl;

import com.example.springcore.model.UsersBean;
import com.example.springcore.repository.UsersRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class UsersRepositoryCustomImpl implements UsersRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    public List<UsersBean> getAllUsersBean() {
        Query query = em.createNamedQuery("getAllUsersBean", UsersBean.class);
        query.setParameter("userName", "2");
        return query.getResultList();
    }

    @Override
    public List<String> getByUserName(String senderName) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("with abc as ( ");
        sqlBuilder.append("             select cf.users_id as id from users c inner join users_friend cf on (c.id=cf.users_id or c.id = cf.friend_id)");
        sqlBuilder.append("             where user_name =:senderName  ");
        sqlBuilder.append("             union ");
        sqlBuilder.append("             select cf.friend_id as id from users c inner join users_friend cf on (c.id=cf.users_id or c.id = cf.friend_id)");
        sqlBuilder.append("             where user_name =:senderName");
        sqlBuilder.append("            ) ");
        sqlBuilder.append(" select user_Name from users where id in (select id from abc) and user_Name !=:senderName");
        Query query = em.createNativeQuery(sqlBuilder.toString());
        query.setParameter("senderName", senderName);
        return query.getResultList();
    }


}
