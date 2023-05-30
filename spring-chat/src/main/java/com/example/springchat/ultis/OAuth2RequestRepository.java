package com.example.springchat.ultis;

import com.example.springcore.utils.AuthenticationKey;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
public class OAuth2RequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(request, "oauth2_auth_request");
        if (cookie == null) {
            return null;
        }
        return CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest != null) {
            CookieUtils.addCookie(response, AuthenticationKey.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                    CookieUtils.serialize(authorizationRequest), AuthenticationKey.cookieExpireSeconds);
            String parameter = request.getParameter(AuthenticationKey.REDIRECT_URL_PARAM_COOKIE_NAME);
            if (StringUtils.isNotBlank(parameter)) {
                CookieUtils.addCookie(response, AuthenticationKey.REDIRECT_URL_PARAM_COOKIE_NAME, parameter, AuthenticationKey.cookieExpireSeconds);
            } else {
                removeAuthorizationRequestCookies(request, response);
            }
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, AuthenticationKey.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, AuthenticationKey.REDIRECT_URL_PARAM_COOKIE_NAME);
    }
}
