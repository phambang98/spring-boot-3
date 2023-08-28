package com.example.spring.rest.api.controller;


import com.example.core.entity.MenuDetail;
import com.example.core.repository.MenuDetailRepository;
import com.example.spring.rest.api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;


}
