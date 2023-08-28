package com.example.spring.rest.api.service;

import com.example.core.entity.MenuDetail;
import com.example.core.repository.MenuDetailRepository;
import com.example.core.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository moviesRepository;


    @Autowired
    private MenuDetailRepository menuDetailRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void abc() {
        MenuDetail menuDetail1 = new MenuDetail();
        menuDetail1.setIdMenu(1l);
        menuDetail1.setOnClick("CreateDoc");
        menuDetail1.setDescription("Value123123123Create");
        menuDetailRepository.save(menuDetail1);
    }
}
