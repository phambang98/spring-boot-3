package com.example.springcore.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "CHAT")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_ID")
    private Long chatId;

    @Column(name = "USER_ID1")
    private Long userId1;

    @Column(name = "USER_ID2")
    private Long userId2;

    @Column(name = "BLOCKED_BY")
    private Long blockedBy;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    public Chat() {
    }

    public Chat(Long chatId, Long userId1, Long userId2, Long blockedBy, Date createdAt, Date updatedAt) {
        this.chatId = chatId;
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.blockedBy = blockedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getUserId1() {
        return userId1;
    }

    public void setUserId1(Long userId1) {
        this.userId1 = userId1;
    }

    public Long getUserId2() {
        return userId2;
    }

    public void setUserId2(Long userId2) {
        this.userId2 = userId2;
    }

    public Long getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(Long blockedBy) {
        this.blockedBy = blockedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
