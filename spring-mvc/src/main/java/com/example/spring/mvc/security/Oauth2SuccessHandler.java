package com.example.spring.mvc.security;

import com.example.spring.mvc.model.CustomOAuth2Users;
import com.example.spring.mvc.service.UsersService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsersService usersService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2Users oauthUser = (CustomOAuth2Users) authentication.getPrincipal();
        usersService.processOAuthPostLogin(oauthUser);
        response.sendRedirect("/home");
    }
}
