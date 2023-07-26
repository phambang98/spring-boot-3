package com.example.core.model;


public class JWTToken {

    private String accessToken;
    private String id;
    private String name;
    private String email;

    private String imageUrl;
    private String tokenType;


    public JWTToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public JWTToken(String accessToken, String id, String name, String email, String imageUrl, String tokenType) {
        this.accessToken = accessToken;
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}