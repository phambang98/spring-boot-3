package com.example.springcore.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "USERS")
//@SqlResultSetMappings({
//        @SqlResultSetMapping(
//                name = "UsersBeanMapping",
//                classes = @ConstructorResult(
//                        targetClass = UsersBean.class,
//                        columns = {
//                                @ColumnResult(name = "id", type = Long.class),
//                                @ColumnResult(name = "userName"),
//                                @ColumnResult(name = "email"),
//                                @ColumnResult(name = "password"),
//                                @ColumnResult(name = "lastUpdateDate", type = Date.class)
//                        }))})
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL_VERIFIED")
    private Boolean emailVerified;

    @Column(name = "ACCOUNT_LOCKED")
    private Boolean accountLocked;

    @Column(name = "ACCOUNT_EXPIRED")
    private Boolean accountExpired;

    @Column(name = "CREDENTIALS_EXPIRED")
    private Boolean credentialsExpired;
    @Column(name = "ROLES")
    private String roles;
    @Column(name = "STATUS")
    private String status;

    @Column(name = "PROVIDER")
    private String provider;

    @Column(name = "PROVIDER_ID")
    private String providerId;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "BLOCKED_BY")
    private String blockBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public Boolean getCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(Boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBlockBy() {
        return blockBy;
    }

    public void setBlockBy(String blockBy) {
        this.blockBy = blockBy;
    }
}