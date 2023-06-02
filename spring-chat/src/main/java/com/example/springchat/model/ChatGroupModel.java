package com.example.springchat.model;

import java.util.List;

public class ChatGroupModel {
    private List<String> listUserName;
    private String nameChatGroup;

    public List<String> getListUserName() {
        return listUserName;
    }

    public void setListUserName(List<String> listUserName) {
        this.listUserName = listUserName;
    }

    public String getNameChatGroup() {
        return nameChatGroup;
    }

    public void setNameChatGroup(String nameChatGroup) {
        this.nameChatGroup = nameChatGroup;
    }
}
