package com.example.core.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Table
@Entity(name = "REFRESH_TOKEN")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;

}