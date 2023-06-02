package com.example.springcore.repository;

import com.example.springcore.entity.Chat;
import com.example.springcore.model.ChatModel;
import com.example.springcore.model.StatusModel;

import java.util.List;

public interface ChatRepositoryCustom {

    List<ChatModel> getFriendList(Long userId);

    List<StatusModel> getFriendStatusByUserId(Long userId);

    List<Chat> findByUserId(Long userId);

    Chat findByUserId1AndUserId2AndChatType(Long senderId, Long recipientId, String chatType);

}
