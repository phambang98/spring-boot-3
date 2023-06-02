package com.example.springcore.enums;

public enum ChatType {
    NORMAL("NORMAL", "TRÒ CHUYỆN CÁ NHÂN"),
    GROUP("GROUP", "TRÒ CHUYỆN NHÓM");

    ChatType(String id, String description) {
        this.id = id;
        this.description = description;
    }

    private String id;
    private String description;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
