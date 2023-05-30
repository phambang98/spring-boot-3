package com.example.springcore.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "USERS_FRIEND")
public class UsersFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USERS_ID")
    private Long usersId;

    @Column(name = "FRIEND_ID")
    private Long friendId;

    @Column(name = "REQUEST_CLIENT_ID")
    private Long requestUsersId;

    @Column(name = "DATE_TIME")
    private String dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) {
        this.usersId = usersId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public Long getRequestUsersId() {
        return requestUsersId;
    }

    public void setRequestUsersId(Long requestUsersId) {
        this.requestUsersId = requestUsersId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
