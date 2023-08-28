package com.example.spring.rest.api.service;

import com.example.core.entity.Menu;
import com.example.core.entity.MenuDetail;
import com.example.core.repository.MenuDetailRepository;
import com.example.core.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MenuService {


    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MenuDetailRepository menuDetailRepository;

    public List<Menu> getAllMenu() {
        return menuRepository.findAll();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void cc() {
        Menu menu1 = new Menu(1l, "file", "file", new Date(), "file", new Date(), "2000");
        menuRepository.save(menu1);
        abc();
        String a = null;
        a.toString();
        Menu menu2 = new Menu(2l, "image", "image", new Date(), "image", new Date(), "500");
        menuRepository.save(menu2);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    public void abc() {
        MenuDetail menuDetail1 = new MenuDetail();
        menuDetail1.setIdMenu(1l);
        menuDetail1.setOnClick("CreateDoc");
        menuDetail1.setDescription("Value123123123Create");
        menuDetailRepository.save(menuDetail1);
    }

}
