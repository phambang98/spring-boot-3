package com.example.springcore.repository;

import com.example.springcore.entity.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatGroupRepository extends JpaRepository<ChatGroup, Long> {


    @Query("select userId from ChatGroup where chatId =?1")
    List<Long> getUserIdByChatId(Long chatId);
}
