package com.example.core.repository;

import com.example.core.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatId(Long chatId);

    Message findByMessageId(Long messageId);
}
