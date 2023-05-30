package com.example.springapi.service;

import com.example.springcore.entity.Menu;
import com.example.springcore.repository.MenuRepository;
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
