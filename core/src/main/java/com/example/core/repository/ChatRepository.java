package com.example.core.repository;

import com.example.core.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends JpaRepository<Chat, Long>, ChatRepositoryCustom {

    Chat findByChatId(Long chatId);
}
