package com.example.springchat.service;

import com.example.springchat.error.ResourceNotFoundException;
import com.example.springchat.model.UserModel;
import com.example.springchat.security.UserPrincipal;
import com.example.springcore.entity.Users;
import com.example.springcore.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUserName(username);
    }

    public UserDetails getUserByUserName(String email) {
        Users users = usersRepository.findByUserName(email);
        return UserPrincipal.create(new ModelMapper().map(users, UserModel.class), new HashMap<>(),
                Arrays.stream(users.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

    public Boolean existsByUserName(String userName) {
        return usersRepository.existsByUserName(userName);
    }

    public UserModel saveUser(UserModel userModel) {
        ModelMapper modelMapper = new ModelMapper();
        Users users = usersRepository.save(modelMapper.map(userModel, Users.class));
        return modelMapper.map(users, UserModel.class);
    }

    public UserDetails getUserById(Long id) throws ResourceNotFoundException {
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            Collection<GrantedAuthority> authorises = Arrays.stream(users.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            return UserPrincipal.create(new ModelMapper().map(users, UserModel.class), new HashMap<>(), authorises);
        } else {
            throw new ResourceNotFoundException("User - id:" + id);
        }
    }

    public UserModel getUserProfile(Long id) throws ResourceNotFoundException {
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            return new ModelMapper().map(users, UserModel.class);
        }
        throw new ResourceNotFoundException("User - id:" + id);
    }
}