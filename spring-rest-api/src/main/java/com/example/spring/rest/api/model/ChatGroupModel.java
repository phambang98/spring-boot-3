package com.example.spring.rest.api.model;

import lombok.Data;

import java.util.List;

@Data
public class ChatGroupModel {
    private List<String> listUserName;
    private String nameChatGroup;
}
