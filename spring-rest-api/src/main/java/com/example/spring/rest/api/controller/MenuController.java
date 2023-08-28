package com.example.spring.rest.api.controller;

import com.example.core.entity.MenuDetail;
import com.example.spring.rest.api.service.MenuService;
import com.example.core.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api")
public class MenuController {

    @Autowired
    private MenuService menuService;


    @GetMapping(value = "menu")
    public ResponseEntity<List<Menu>> getAllMenu() {
        return new ResponseEntity<>(menuService.getAllMenu(), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<Void> cc() {
        menuService.cc();
        return  ResponseEntity.ok().build();
    }
}
