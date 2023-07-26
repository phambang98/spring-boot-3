package com.example.core.model;

public class StatusModel {
    private Long chatId;
    private Long userId;
    private String userName;

    private String status;
    private String lastTimeLogin;

    public StatusModel() {
    }

    public StatusModel(Long chatId, Long userId, String userName, String status, String lastTimeLogin) {
        this.chatId = chatId;
        this.userId = userId;
        this.userName = userName;
        this.status = status;
        this.lastTimeLogin = lastTimeLogin;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
