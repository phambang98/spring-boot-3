package com.example.core.repository;

import com.example.core.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movies,Long> {
}
