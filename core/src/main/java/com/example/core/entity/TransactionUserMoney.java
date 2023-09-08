package com.example.core.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "TRANSACTION_USER_MONEY")
public class TransactionUserMoney extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "AMOUNT_MONEY")
    private BigDecimal amountMoney;
}
