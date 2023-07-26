package com.example.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "RATINGS")
public class Ratings  extends AbstractEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "MOVIE_ID")
	private Long movieId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "LIKES")
	private Long likes;

	@Column(name = "DISLIKE")
	private Long dislike;

	@Column(name = "RATING")
	private Double rating;

}