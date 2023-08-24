package com.example.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ChatModel implements Serializable {

    private Long chatId;
    private Long userId;
    private String userName;
    private String imageUrl;
    private Long blockedBy;
    private String status;
    private String lastMsg;
    private Date lastTimeMsg;
    private String chatType;


    public ChatModel(Long chatId, Long userId, String userName, String imageUrl, Long blockedBy, String status, String lastMsg, Date lastTimeMsg, String chatType) {
        this.chatId = chatId;
        this.userId = userId;
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.blockedBy = blockedBy;
        this.status = status;
        this.lastMsg = lastMsg;
        this.lastTimeMsg = lastTimeMsg;
        this.chatType = chatType;
    }
}
