package com.example.springapi.controller;

import com.example.springapi.service.MenuService;
import com.example.springcore.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
