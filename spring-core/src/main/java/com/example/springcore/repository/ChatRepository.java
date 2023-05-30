package com.example.springcore.repository;

import com.example.springcore.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long>, ChatRepositoryCustom {

    Chat findByUserId1AndUserId2OrUserId1AndUserId2(Long user1, Long friend2, Long friend1, Long user2);

    Chat findByChatId(Long chatId);
}
