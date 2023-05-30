package com.example.springchat.model;

public class UserProfile {
    private Long id;
    private String email;
    private String userName;
    private String imageUrl;

    public UserProfile() {
    }

    public UserProfile(Long id, String email, String userName, String imageUrl) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
