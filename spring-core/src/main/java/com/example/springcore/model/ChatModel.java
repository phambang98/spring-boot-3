package com.example.springcore.model;

import java.util.Date;

public class ChatModel {

    private Long chatId;
    private Long userId;
    private String email;
    private String userName;
    private String imageUrl;
    private Long blockedBy;
    private String status;
    private String lastTimeLogin;
    private String lastMsg;
    private Date lastTimeMsg;
    private String chatType;

    public ChatModel() {
    }

    public ChatModel(Long chatId, Long userId, String email, String userName, String imageUrl, Long blockedBy,
                     String status, String lastTimeLogin, String lastMsg, Date lastTimeMsg, String chatType) {
        this.chatId = chatId;
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.blockedBy = blockedBy;
        this.status = status;
        this.lastTimeLogin = lastTimeLogin;
        this.lastMsg = lastMsg;
        this.lastTimeMsg = lastTimeMsg;
        this.chatType = chatType;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(Long blockedBy) {
        this.blockedBy = blockedBy;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastTimeLogin() {
        return lastTimeLogin;
    }

    public void setLastTimeLogin(String lastTimeLogin) {
        this.lastTimeLogin = lastTimeLogin;
    }


    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public Date getLastTimeMsg() {
        return lastTimeMsg;
    }

    public void setLastTimeMsg(Date lastTimeMsg) {
        this.lastTimeMsg = lastTimeMsg;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }
}
