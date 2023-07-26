package com.example.core.repository;

import com.example.core.entity.Chat;
import com.example.core.model.ChatModel;
import com.example.core.model.StatusModel;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ChatRepositoryCustom {

    @Cacheable(value = "users", key = "T(java.lang.String).format('%s', #p0)")
    List<ChatModel> getFriendList(Long userId);

    List<StatusModel> getFriendStatusByUserId(Long userId);

    List<Chat> findByUserId(Long userId);

    Chat findByUserId1AndUserId2AndChatType(Long senderId, Long recipientId, String chatType);

}
