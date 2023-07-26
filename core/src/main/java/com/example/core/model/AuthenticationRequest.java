package com.example.core.model;

public class AuthenticationRequest {
    private String email;
    private String password;

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthenticationRequest() {
    }

    public static AuthenticationRequest builder() {
        return new AuthenticationRequest();
    }

    public AuthenticationRequest email(String email) {
        this.email = email;
        return this;
    }

    public AuthenticationRequest password(String password) {
        this.password = password;
        return this;
    }

    public AuthenticationRequest build() {
        return new AuthenticationRequest(email, password);
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
}
