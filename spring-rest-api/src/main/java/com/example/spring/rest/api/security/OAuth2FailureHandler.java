package com.example.spring.rest.api.security;

import com.example.spring.rest.api.ultis.OAuth2RequestRepository;
import com.example.core.utils.AuthenticationKey;
import com.example.core.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private OAuth2RequestRepository oAuth2RequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        var cookie = CookieUtils.getCookie(request, AuthenticationKey.REDIRECT_URL_PARAM_COOKIE_NAME);
        String targetUrl = cookie != null ? cookie.getValue() : "/";
        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();
        oAuth2RequestRepository.removeAuthorizationRequestCookies(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
