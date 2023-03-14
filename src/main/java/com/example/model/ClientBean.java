package com.example.model;

import java.util.Date;

public class ClientBean {

    private Long id;
    private String userName;

    private String email;

    private String password;

    private Date lastUpdateDate;

    public ClientBean(Long id, String userName, String email, String password, Date lastUpdateDate) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.lastUpdateDate = lastUpdateDate;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
