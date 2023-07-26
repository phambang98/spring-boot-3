package com.example.core.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "USER_STATUS")
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_STATUS_ID")
    private Long userStatusId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "LAST_TIME_LOGIN")
    private String lastTimeLogin;

    public Long getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(Long userStatusId) {
        this.userStatusId = userStatusId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
