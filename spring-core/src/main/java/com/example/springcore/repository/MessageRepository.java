package com.example.springcore.repository;

import com.example.springcore.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatId(Long chatId);

    Message findByMessageId(Long messageId);
}
