package com.example.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "REVIEWS")
public class Reviews extends AbstractEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "PARENT_ID")
	private Long parentId;

	@Column(name = "MOVIE_ID")
	private Long movieId;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "COMMENTS")
	private String comments;

}