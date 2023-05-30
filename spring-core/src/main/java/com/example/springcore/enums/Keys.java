package com.example.springcore.enums;

public enum Keys {

    USER_NAME_NOT_FOUND("User Name not found"),
    INCORRECT_PASSWORD("Incorrect Password!"),
    AUTHORIZATION_REQUEST_NOT_FOUND("Authorization request not found!");

    private final String description;

    Keys(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
