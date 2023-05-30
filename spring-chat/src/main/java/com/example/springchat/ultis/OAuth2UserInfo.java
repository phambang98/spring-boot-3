package com.example.springchat.ultis;

import com.example.springchat.enums.AuthProvider;
import com.example.springchat.error.OAuth2AuthenticationProcessingException;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();


    public static class FacebookOAuth2UserInfo extends OAuth2UserInfo {

        public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        @Override
        public String getId() {
            return (String) attributes.get("id");
        }

        @Override
        public String getName() {
            return (String) attributes.get("name");
        }

        @Override
        public String getEmail() {
            return (String) attributes.get("email");
        }

        @Override
        public String getImageUrl() {
            if (attributes.containsKey("picture")) {
                Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("email");
                if (pictureObj.containsKey("data")) {
                    return null;
                }
            }
            return null;
        }
    }

    public static class GoogleOAuth2UserInfo extends OAuth2UserInfo {

        public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        @Override
        public String getId() {
            return (String) attributes.get("sub");
        }

        @Override
        public String getName() {
            return (String) attributes.get("name");
        }

        @Override
        public String getEmail() {
            return (String) attributes.get("email");
        }

        @Override
        public String getImageUrl() {
            return (String) attributes.get("picture");
        }
    }

    public static class GitHubAuth2UserInfo extends OAuth2UserInfo {

        public GitHubAuth2UserInfo(Map<String, Object> attributes) {
            this.attributes = attributes;
        }

        @Override
        public String getId() {
            return String.valueOf(attributes.get("id"));
        }

        @Override
        public String getName() {
            return (String) attributes.get("login");
        }

        @Override
        public String getEmail() {
            return (String) attributes.get("email");
        }

        @Override
        public String getImageUrl() {
            return (String) attributes.get("avatar_url");
        }
    }

    public class OAuth2UserInfoFactory {
        public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) throws OAuth2AuthenticationProcessingException {
            if (registrationId.equals(AuthProvider.google.toString())) {
                return new GoogleOAuth2UserInfo(attributes);
            } else if (registrationId.equals(AuthProvider.facebook.toString())) {
                return new FacebookOAuth2UserInfo(attributes);
            } else if (registrationId.equals(AuthProvider.github.toString())) {
                return new GitHubAuth2UserInfo(attributes);
            } else {
                throw new OAuth2AuthenticationProcessingException("Sorry! Login with $registrationId is not supported yet.");
            }
        }
    }
}
