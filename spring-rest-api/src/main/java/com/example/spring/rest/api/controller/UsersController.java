package com.example.spring.rest.api.controller;

import com.example.core.entity.Users;
import com.example.spring.rest.api.model.UserProfile;
import com.example.spring.rest.api.security.SecurityUtils;
import com.example.spring.rest.api.security.UserPrincipal;
import com.example.spring.rest.api.service.UsersService;
import com.example.core.error.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("user/me")
    public UserProfile getMyProfile() throws ResourceNotFoundException {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return new ModelMapper().map(usersService.getUserProfile(user.getId()), UserProfile.class);
    }

    @GetMapping("user/exists")
    public ResponseEntity<Boolean> existsByUserName(@RequestParam("userName") String userName) throws ResourceNotFoundException {
        if (SecurityUtils.getCurrentIdLogin().getUsername().equals(userName)) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(usersService.existsByUserName(userName));
    }

    @GetMapping("user")
    public ResponseEntity<List<Users>> findAll(Pageable pageable, @RequestParam("listEmail") List<String> listEmail) {
        return new ResponseEntity<>(usersService.findAll(pageable, listEmail), HttpStatus.OK);
    }
}