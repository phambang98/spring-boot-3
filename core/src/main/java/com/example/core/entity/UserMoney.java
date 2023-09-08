package com.example.core.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "USER_MONEY")
public class UserMoney extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "TOTAL_MONEY")
    private BigDecimal totalMoney;
}
