package com.example.spring.rest.api.service;

import com.example.core.entity.Menu;
import com.example.core.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getAllMenu(){
        return menuRepository.findAll();
    }
}
