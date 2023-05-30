package com.example.springchat.model;

import jakarta.validation.constraints.NotBlank;

public class TokenVerify {

    @NotBlank(message = "Token cannot be null")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
