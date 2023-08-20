package com.example.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "PRIZES")
public class Prizes extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRIZE_GROUP_ID")
    private Long prizeGroupId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LUCKY_NUMBER_FROM")
    private Long luckNumberFrom;

    @Column(name = "LUCKY_NUMBER_TO")
    private Long luckNumberTo;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "DISPLAY_NUMBER")
    private Long displayNumber;

    @Column(name = "QUANTITY")
    private Long quantity;
}
