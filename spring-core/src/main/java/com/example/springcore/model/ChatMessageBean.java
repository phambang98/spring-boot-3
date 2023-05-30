package com.example.springcore.model;

public class ChatMessageBean {

    private String id;

    private String type;

    private String content;

    private String usersFrom;

    private String usersTo;

    private Long displayOrder;

    private String timeSend;

    public ChatMessageBean() {
    }

    public ChatMessageBean(String id, String type, String content, String usersFrom, String usersTo, Long displayOrder, String timeSend) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.usersFrom = usersFrom;
        this.usersTo = usersTo;
        this.displayOrder = displayOrder;
        this.timeSend = timeSend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsersFrom() {
        return usersFrom;
    }

    public void setUsersFrom(String usersFrom) {
        this.usersFrom = usersFrom;
    }

    public String getUsersTo() {
        return usersTo;
    }

    public void setUsersTo(String usersTo) {
        this.usersTo = usersTo;
    }

    public Long getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(String timeSend) {
        this.timeSend = timeSend;
    }
}
