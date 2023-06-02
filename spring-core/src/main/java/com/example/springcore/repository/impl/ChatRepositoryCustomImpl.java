package com.example.springcore.repository.impl;

import com.example.springcore.entity.Chat;
import com.example.springcore.enums.ChatType;
import com.example.springcore.model.ChatModel;
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
    public List<ChatModel> getFriendList(Long userId) {
        try {
            String sqlStr =
                    "select * from (select c.chat_id as chatId,u.id as userId, u.email ," +
                            "              u.user_name as userName, u.image_Url as imageUrl ,c.BLOCKED_BY as blockedBy," +
                            "              case when c.BLOCKED_BY is null then us.status else null end as status, " +
                            "              us.last_time_login as lastTimeLogin," +
                            "              (select case when CONTENT_TYPE ='file' and content is null " +
                            "                      then concat((case when sender_id =:userId then 'Bạn:' else '' end),concat('send ',(select count(1) from file where message_id =mss.message_id)),' file') " +
                            "                      else concat((case when sender_id =:userId then 'Bạn:' else '' end),content) end from message mss where chat_id = c.chat_id order by CREATE_AT desc limit 1 ) " +
                            "                      as lastMsg," +
                            "               max(m.CREATE_AT) as lastTimeMsg,'NORMAL' as chatType " +
                            "       from chat c inner join users u on (c.user_Id1 = u.id or c.user_Id2 = u.id) and u.id !=:userId " +
                            "                   inner join user_status us on us.user_id = u.id " +
                            "                   left join message m on c.chat_id = m.chat_id " +
                            "       where c.user_Id1 = :userId or c.user_Id2 = :userId and c.chat_type ='NORMAL' " +
                            "       group by u.id, u.email ,u.user_name,u.image_Url,c.BLOCKED_BY,us.status,us.last_time_login,c.chat_id " +
                            "       union all" +
                            "       select c.chat_id as chatId,0 as userId,null as email ," +
                            "              c.display_name as userName ,c.image_url as imageUrl ," +
                            "              0 as blockedBy," +
                            "              'ONLINE' as status, " +
                            "              null as lastTimeLogin," +
                            "              (select case when CONTENT_TYPE ='file' and content is null " +
                            "                      then concat((case when sender_id =:userId then 'Bạn:' else '' end),concat('send ',(select count(1) from file where message_id =mss.message_id)),' file') " +
                            "                      else concat((case when sender_id =:userId then 'Bạn:' else '' end),content) end from message mss where chat_id = c.chat_id order by CREATE_AT desc limit 1 ) " +
                            "                      as lastMsg," +
                            "               max(m.CREATE_AT) as lastTimeMsg,'GROUP' as chatType " +
                            "       from chat c inner join chat_group cg on cg.chat_id = c.chat_id " +
                            "                   inner join users u on u.id =cg.user_id " +
                            "                   left join message m on c.chat_id = m.chat_id " +
                            "       where u.id = :userId and c.chat_type = 'GROUP' " +
                            "       group by u.id, c.image_Url, c.display_name, c.chat_id " +
                            ") order by lastTimeMsg desc ";
            Query query = em.createNativeQuery(sqlStr, "ChatModelMapping");
            query.setParameter("userId", userId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<StatusModel> getFriendStatusByUserId(Long userId) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select c.chat_id as chatId,u.id as userId,u.user_name as userName,us.status,us.last_time_login as lastTimeLogin ");
        sqlBuilder.append("from chat c inner join users u on (c.user_Id1 = u.id or c.user_Id2 = u.id) and u.id !=:userId ");
        sqlBuilder.append("            inner join user_status us on us.USER_ID = u.id ");
        sqlBuilder.append("where (c.user_Id1 = :userId or c.user_Id2 = :userId) and c.BLOCKED_BY is null ");
        sqlBuilder.append("order by CREATED_AT desc, UPDATED_AT desc ");
        Query query = em.createNativeQuery(sqlBuilder.toString(), "StatusModelMapping");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Chat> findByUserId(Long userId) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select c.* ");
        sqlBuilder.append("from chat c inner join users u on (c.user_Id1 = u.id or c.user_Id2 = u.id) and u.id !=:userId ");
        sqlBuilder.append("            inner join User_Status us on us.USER_ID = u.id ");
        sqlBuilder.append("where (c.user_Id1 = :userId or c.user_Id2 = :userId) and c.BLOCKED_BY is null ");
        sqlBuilder.append("order by CREATED_AT desc, UPDATED_AT desc ");
        Query query = em.createNativeQuery(sqlBuilder.toString(), Chat.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Chat findByUserId1AndUserId2AndChatType(Long senderId, Long recipientId, String chatType) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("select c.* from chat c  ");

        if (chatType.equals(ChatType.NORMAL.getId())) {
            sqlBuilder.append("where ((c.user_id1 = :senderId and c.user_id2 = :recipientId) ");
            sqlBuilder.append("       or(c.user_id1 = :recipientId and c.user_id2 = :senderId)) ");
        } else {
            sqlBuilder.append("inner join chat_group cg on c.chat_id =cg.chat_id ");
            sqlBuilder.append("where cg.user_id =:senderId ");
        }
        sqlBuilder.append("and c.chat_type =:chatType limit 1");
        Query query = em.createNativeQuery(sqlBuilder.toString(), Chat.class);
        query.setParameter("senderId", senderId);
        if (chatType.equals(ChatType.NORMAL.getId())) {
            query.setParameter("recipientId", recipientId);
        }
        query.setParameter("chatType", chatType);
        return (Chat) query.getSingleResult();
    }
}
