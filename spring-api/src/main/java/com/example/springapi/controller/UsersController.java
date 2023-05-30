package com.example.springapi.controller;

import com.example.springcore.entity.Users;
import com.example.springcore.model.UsersBean;
import com.example.springcore.model.RecordNotFoundException;
import com.example.springapi.service.UsersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name = "bearerAuth")
@SecurityRequirement(name = "basicAuth")
@SecurityRequirement(name = "GoogleOauth2")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping(value = "users", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> list = usersService.getAllUsers();

        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "users/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Users> getUsersById(@PathVariable("id") Long id) throws RecordNotFoundException {
        Users entity = usersService.getUsersById(id);
        return new ResponseEntity<>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("users")
    public ResponseEntity<Users> createOrUpdateUsers(Users users) {
        Users updated = usersService.createOrUpdateUsers(users);
        return new ResponseEntity<>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("users/{id}")
    public HttpStatus deleteUsersById(@PathVariable("id") Long id) throws RecordNotFoundException {
        usersService.deleteUsersById(id);
        return HttpStatus.OK;
    }

}