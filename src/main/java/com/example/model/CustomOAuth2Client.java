package com.example.model;

import com.example.enums.Provider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2Client implements OAuth2User {

    private OAuth2User oauth2User;

    private String registrationId;

    public CustomOAuth2Client(OAuth2User oauth2User, String registrationId) {
        this.oauth2User = oauth2User;
        this.registrationId = registrationId;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        if (this.registrationId.equalsIgnoreCase(Provider.GITHUB.name())) {
            return oauth2User.getAttribute("login");
        }
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.getAttribute("email");
    }
}
