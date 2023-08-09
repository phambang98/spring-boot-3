package com.example.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MOVIE_GENRES")
public class MovieGenres extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MOVIE_ID")
    private Long movieId;

    @Column(name = "GENRE_ID")
    private Long genreId;
}
