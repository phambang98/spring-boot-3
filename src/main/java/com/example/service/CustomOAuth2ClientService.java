package com.example.service;

import com.example.controller.AuthController;
import com.example.model.CustomOAuth2Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2ClientService extends DefaultOAuth2UserService {

    Logger logger = LoggerFactory.getLogger(CustomOAuth2ClientService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        logger.info("CustomOAuth2UserService invoked");
        return new CustomOAuth2Client(user, registrationId);
    }

}
