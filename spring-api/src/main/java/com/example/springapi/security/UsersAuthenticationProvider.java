package com.example.springapi.security;

import com.example.springapi.error.IncorrectPasswordException;
import com.example.springcore.entity.Users;
import com.example.springcore.enums.Keys;
import com.example.springcore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsersAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsersRepository usersRepository;


    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        final Users users = usersRepository.findByUserName(username);
        if (users == null) {
            throw new UsernameNotFoundException(Keys.USER_NAME_NOT_FOUND.getDescription());
        }
        UserDetails userDetails = new UsersSecurity(users);
        boolean checkPassword = passwordEncoder.matches(password, userDetails.getPassword());
        if (!checkPassword) {
            throw new IncorrectPasswordException(Keys.INCORRECT_PASSWORD.getDescription());
        }
        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return authenticationType
                .equals(UsernamePasswordAuthenticationToken.class);
    }
}