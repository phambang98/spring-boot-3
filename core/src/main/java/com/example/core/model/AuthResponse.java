package com.example.core.model;

import lombok.Data;

@Data
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer ";
    private String userName;
    private String email;
    private String imageUrl;
    private Long id;

    public AuthResponse(String accessToken, String refreshToken, String userName, String email, String imageUrl, Long id) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userName = userName;
        this.email = email;
        this.imageUrl = imageUrl;
        this.id = id;
    }
}
