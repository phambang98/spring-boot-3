package com.example.springchat.security;

import com.example.springchat.error.BadRequestException;
import com.example.springchat.model.CustomOAuth2Users;
import com.example.springchat.security.jwt.TokenProvider;
import com.example.springchat.service.UsersService;
import com.example.springchat.ultis.CookieUtils;
import com.example.springchat.ultis.OAuth2RequestRepository;
import com.example.springcore.utils.AuthenticationKey;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;


@Component
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UsersService usersService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private OAuth2RequestRepository oAuth2RequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


    @Override
    public String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        var cookie = CookieUtils.getCookie(request, AuthenticationKey.REDIRECT_URL_PARAM_COOKIE_NAME);
        String redirectUrl = cookie != null ? cookie.getValue() : "";

        if (redirectUrl.isEmpty()
//                && !isAuthorizedRedirectUrl(redirectUrl)
        ) {
            try {
                throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URL and can't proceed with the authentication");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }

        return UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("token", tokenProvider.createToken(authentication))
                .build().toUriString();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        oAuth2RequestRepository.removeAuthorizationRequestCookies(request, response);
    }

//    private Boolean isAuthorizedRedirectUrl(String uri) {
//
//        var clientRedirectUri = URI.create(uri);
//        return CollectionUtils.isEmpty(appProperties.getOauth2().getAuthorizedRedirectUrls().stream().filter(x -> {
//            var authorizedURL = URI.create(x);
//            if (authorizedURL.getHost().toUpperCase().equals(clientRedirectUri.getHost().toUpperCase())) {
//                return true;
//            }
//            return false;
//        }).collect(Collectors.toList()));
//
//    }
}
