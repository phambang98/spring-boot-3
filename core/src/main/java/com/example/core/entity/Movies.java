package com.example.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MOVIES")
public class Movies extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "RELEASE_YEAR")
	private Long releaseYear;

	@Column(name = "STORY")
	private String story;

	@Column(name = "FILE_ID")
	private String fileId;

}