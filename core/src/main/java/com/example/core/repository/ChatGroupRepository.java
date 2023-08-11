package com.example.core.repository;

import com.example.core.entity.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatGroupRepository extends JpaRepository<ChatGroup, Long> {


    @Query("select userId from ChatGroup where chatId =?1")
    List<Long> getUserIdByChatId(Long chatId);

    @Modifying
    int deleteByChatIdAndUserId(Long chatId, Long userId);


    @Modifying
    @Query("delete from ChatGroup where chatId =?1 and userId in (select userName from Users where userName =?2)")
    int deleteByChatIdAndUserNameIn(Long chatId, List<String> userName);
}
