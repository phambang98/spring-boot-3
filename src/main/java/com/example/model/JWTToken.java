package com.example.model;

import java.util.Date;

public class JWTToken  {

    private String accessToken;

    public JWTToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}