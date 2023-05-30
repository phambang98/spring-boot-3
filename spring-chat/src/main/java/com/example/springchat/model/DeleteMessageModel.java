package com.example.springchat.model;

public class DeleteMessageModel {
    private Long recipientId;
    private Long messageId;

    public DeleteMessageModel(Long recipientId, Long messageId) {
        this.recipientId = recipientId;
        this.messageId = messageId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public Long getMessageId() {
        return messageId;
    }
}
