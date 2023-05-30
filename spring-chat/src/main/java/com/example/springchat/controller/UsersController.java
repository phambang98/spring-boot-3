package com.example.springchat.controller;

import com.example.springchat.error.ResourceNotFoundException;
import com.example.springchat.model.UserProfile;
import com.example.springchat.security.SecurityUtils;
import com.example.springchat.security.UserPrincipal;
import com.example.springchat.service.UsersService;
import com.example.springcore.entity.Users;
import com.example.springcore.model.UsersBean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

}