package com.example.springchat.model;

public class MessageRequest {

    private Long recipientId;
    private String content;

    private Long messageId;
    private Boolean updateMessage = false;


    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Boolean getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(Boolean updateMessage) {
        this.updateMessage = updateMessage;
    }
}
