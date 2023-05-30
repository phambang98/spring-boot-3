package com.example.springchat.security;

import com.example.springchat.model.UserModel;
import com.example.springcore.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails, OAuth2User {

    private Long id;
    private String mEmail;
    private String mPassword;
    private Collection<GrantedAuthority> mAuthorities;
    private Map<String, Object> mAttributes;
    private String mUserName;
    private String mImageUrl;

    public UserPrincipal() {
    }
    public UserPrincipal(Users users) {
        this.id = users.getId();
        this.mEmail = users.getEmail();
        this.mPassword = users.getPassword();
        this.mAuthorities = Arrays.stream(users.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        this.mAttributes = new HashMap<>();
        this.mUserName = users.getUserName();
        this.mImageUrl = users.getImageUrl();
    }

    public UserPrincipal(Long id, String mEmail, String mPassword, Collection<GrantedAuthority> mAuthorities, Map<String, Object> mAttributes, String mUserName, String mImageUrl) {
        this.id = id;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mAuthorities = mAuthorities;
        this.mAttributes = mAttributes;
        this.mUserName = mUserName;
        this.mImageUrl = mImageUrl;
    }

    public static UserPrincipal create(UserModel userModel, Map<String, Object> attributes, Collection<GrantedAuthority> authorities) {
        return new UserPrincipal(userModel.getId(), userModel.getEmail(), userModel.getPassword(), authorities, attributes, userModel.getUserName(), userModel.getImageUrl());

    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return mUserName;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return mPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return mAuthorities;
    }

    @Override
    public String getName() {
        return mUserName;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return mAttributes;
    }


}
