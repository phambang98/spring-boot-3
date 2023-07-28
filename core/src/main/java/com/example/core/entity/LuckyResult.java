package com.example.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "LUCKY_RESULT")
public class LuckyResult extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "PRIZE_ID")
    private Long prizeId;

    @Column(name = "LUCKY_NUMBER")
    private Long luckNumber;
}
