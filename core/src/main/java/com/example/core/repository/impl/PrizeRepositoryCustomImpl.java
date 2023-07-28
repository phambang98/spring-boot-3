package com.example.core.repository.impl;

import com.example.core.entity.Prizes;
import com.example.core.repository.PrizeRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

public class PrizeRepositoryCustomImpl implements PrizeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

}
