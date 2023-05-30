package com.example.springmvc.service;

import com.example.springcore.entity.Users;
import com.example.springcore.enums.Provider;
import com.example.springcore.repository.UsersRepository;
import com.example.springmvc.model.CustomOAuth2Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public void processOAuthPostLogin(CustomOAuth2Users customOAuth2Users) {
        String registrationId = customOAuth2Users.getRegistrationId();
        Users existUsers = usersRepository.findByUserNameAndProviderEqualsIgnoreCase(customOAuth2Users.getName(), registrationId);
        //FIXME-TODO
        if (existUsers == null) {
            Users users = new Users();
            Map<String, Object> mapAttr = customOAuth2Users.getAttributes();
            users.setUserName((String) mapAttr.getOrDefault("sub", customOAuth2Users.getEmail()));
            users.setProvider(Provider.GOOGLE.name());
            users.setStatus("A");
            users.setEmail(customOAuth2Users.getEmail());
            usersRepository.save(users);

            System.out.println("Created new user: " + customOAuth2Users.getName());
        }

    }
}