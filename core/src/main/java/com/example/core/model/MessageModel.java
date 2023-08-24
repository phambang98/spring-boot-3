package com.example.core.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class MessageModel implements Serializable {

    private Long messageId;
    private Long senderId;
    private Long recipientId;
    private Long chatId;
    private String content;
    private List<FileModel> files;
    private String contentType;
    private Date createdAt;
    private Date updatedAt;
    private Boolean read;
    private String senderName;
    private String imageUrl;

}
