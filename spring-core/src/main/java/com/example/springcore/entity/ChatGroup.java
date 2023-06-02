package com.example.springcore.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "CHAT_GROUP")
public class ChatGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_GROUP_ID")
    private Long chatGroupId;

    @Column(name = "CHAT_ID")
    private Long chatId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "JOIN_DATE")
    private Date joinDate;
    public ChatGroup() {
    }

    public ChatGroup(Long chatId, Long userId, Date joinDate) {
        this.chatId = chatId;
        this.userId = userId;
        this.joinDate = joinDate;
    }

    public Long getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(Long chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}
