package com.example.spring.rest.api.service;

import com.example.core.model.ResultData;
import com.example.core.model.ForgotPasswordRequest;
import com.example.core.model.UsersBean;
import com.example.spring.rest.api.security.UserPrincipal;
import com.example.core.error.ResourceNotFoundException;
import com.example.core.model.UserModel;
import com.example.core.entity.Users;
import com.example.core.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
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

    public List<Users> findAll(Pageable pageable, List<String> listEmail) {
//        clearCache();
        return usersRepository.findByEmailIn(listEmail);
    }

    public List<UsersBean> getAllUsersBean() throws InterruptedException {
//        clearCache();
        var ss= usersRepository.getAllUsersBean();
        return ss;
    }

    @CacheEvict(value = "users", allEntries = true)
    public void clearCache() {
    }

}