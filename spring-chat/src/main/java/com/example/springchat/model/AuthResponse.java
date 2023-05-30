package com.example.springchat.model;

public class AuthResponse {

    private String accessToken;
    private String tokenType = "Bearer ";
    private String userName;
    private String email;
    private String imageUrl;
    private Long id;

    public AuthResponse(String accessToken, String userName, String email, String imageUrl, Long id) {
        this.accessToken = accessToken;
        this.userName = userName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
