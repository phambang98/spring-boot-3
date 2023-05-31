package com.example.springcore.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "USER_CHAT_GROUP")
public class UserChatGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_CHAT_GROUP_ID")
    private Long userChatGroupId;

    @Column(name = "CHAT_GROUP_ID")
    private Long chatGroupId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "JOIN_DATE")
    private Date joinDate;
    public UserChatGroup() {
    }

    public Long getUserChatGroupId() {
        return userChatGroupId;
    }

    public void setUserChatGroupId(Long userChatGroupId) {
        this.userChatGroupId = userChatGroupId;
    }

    public Long getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(Long chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }
}
