package com.example.springcore.repository;

import com.example.springcore.entity.MenuDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MenuDetailRepository extends JpaRepository<MenuDetail, Long> {
}
