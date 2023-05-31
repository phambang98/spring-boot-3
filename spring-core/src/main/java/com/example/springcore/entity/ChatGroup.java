package com.example.springcore.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "CHAT_GROUP")
public class ChatGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_GROUP_ID")
    private Long chatGroupId;

    @Column(name = "DISPLAY_NAME")
    private Long displayName;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    public ChatGroup() {
    }

    public Long getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(Long chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public Long getDisplayName() {
        return displayName;
    }

    public void setDisplayName(Long displayName) {
        this.displayName = displayName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
