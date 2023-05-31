package com.example.springcore.repository.impl;

import com.example.springcore.model.FriendProfileModel;
import com.example.springcore.model.StatusModel;
import com.example.springcore.repository.ChatRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class ChatRepositoryCustomImpl implements ChatRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<FriendProfileModel> getFriendList(Long userId) {
        try {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("select * from (select u.id as userId, u.email ,u.user_name as userName,u.image_Url as imageUrl ,c.BLOCKED_BY as blockedBy,");
            sqlBuilder.append("                      case when c.BLOCKED_BY is null then us.status else null end as status, ");
            sqlBuilder.append("                      us.last_time_login as lastTimeLogin, ");
            sqlBuilder.append("                      (select case when CONTENT_TYPE ='file' and content is null ");
            sqlBuilder.append("                              then concat((case when sender_id =:userId then 'Bạn:' else '' end),concat('send ',(select count(1) from file where message_id =mss.message_id)),' file') ");
            sqlBuilder.append("                              else concat((case when sender_id =:userId then 'Bạn:' else '' end),content) end from message mss where chat_id = c.chat_id order by CREATE_AT desc limit 1 ) ");
            sqlBuilder.append("                       as lastMsg,");
            sqlBuilder.append("                       max(m.CREATE_AT) as lastTimeMsg ");
            sqlBuilder.append("               from chat c inner join users u on (c.user_Id1 = u.id or c.user_Id2 = u.id) and u.id !=:userId ");
            sqlBuilder.append("                           inner join user_status us on us.user_id = u.id ");
            sqlBuilder.append("                           left join message m on c.chat_id = m.chat_id ");
            sqlBuilder.append("               where c.user_Id1 = :userId or c.user_Id2 = :userId ");
            sqlBuilder.append("               group by u.id, u.email ,u.user_name,u.image_Url,c.BLOCKED_BY,us.status,us.last_time_login,c.chat_id ");
            sqlBuilder.append(") order by lastTimeMsg desc ");
            Query query = em.createNativeQuery(sqlBuilder.toString(), "FriendProfileModelMapping");
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StatusModel> getFriendStatusByUserId(Long userId) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select  u.id as userId,u.user_name as userName,us.status,us.last_time_login as lastTimeLogin ");
        sqlBuilder.append("from chat c inner join users u on (c.user_Id1 = u.id or c.user_Id2 = u.id) and u.id !=:userId ");
        sqlBuilder.append("            inner join user_status us on us.USER_ID = u.id ");
        sqlBuilder.append("where (c.user_Id1 = :userId or c.user_Id2 = :userId) and c.BLOCKED_BY is null ");
        sqlBuilder.append("order by CREATED_AT desc, UPDATED_AT desc ");
        Query query = em.createNativeQuery(sqlBuilder.toString(), "StatusModelMapping");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Long> getFriendIdByUserId(Long userId) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select u.id ");
        sqlBuilder.append("from chat c inner join users u on (c.user_Id1 = u.id or c.user_Id2 = u.id) and u.id !=:userId ");
        sqlBuilder.append("            inner join User_Status us on us.USER_ID = u.id ");
        sqlBuilder.append("where (c.user_Id1 = :userId or c.user_Id2 = :userId) and c.BLOCKED_BY is null ");
        sqlBuilder.append("order by CREATED_AT desc, UPDATED_AT desc ");
        Query query = em.createNativeQuery(sqlBuilder.toString());
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
