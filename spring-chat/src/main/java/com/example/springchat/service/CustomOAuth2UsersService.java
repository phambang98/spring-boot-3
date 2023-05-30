package com.example.springchat.service;

import com.example.springchat.enums.AuthProvider;
import com.example.springchat.error.InternalServerErrorException;
import com.example.springchat.error.OAuth2AuthenticationProcessingException;
import com.example.springchat.model.UserModel;
import com.example.springchat.security.UserPrincipal;
import com.example.springchat.ultis.OAuth2UserInfo;
import com.example.springcore.entity.UserStatus;
import com.example.springcore.entity.Users;
import com.example.springcore.enums.Status;
import com.example.springcore.repository.UserStatusRepository;
import com.example.springcore.repository.UsersRepository;
import com.example.springcore.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2UsersService extends DefaultOAuth2UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        var oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (Exception ex) {
            throw new InternalServerErrorException(ex);
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) throws OAuth2AuthenticationProcessingException {
        var oAuth2UserInfo = OAuth2UserInfo.OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        Users userEntity;
        switch (AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
            case facebook:
                if (oAuth2UserInfo.getId().isEmpty()) {
                    throw new OAuth2AuthenticationProcessingException("FaceBook Id not found from OAuth2 provider facebook");
                }
                userEntity = usersRepository.findByUserName(oAuth2UserInfo.getId());
                break;
            case google:
                if (oAuth2UserInfo.getEmail().isEmpty()) {
                    throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider google");
                }
                userEntity = usersRepository.findByEmail(oAuth2UserInfo.getEmail());
                break;
            case github:
                if (oAuth2UserInfo.getId().isEmpty()) {
                    throw new OAuth2AuthenticationProcessingException("GitHub Id not found from OAuth2 provider github");
                }
                userEntity = usersRepository.findByUserName(oAuth2UserInfo.getId());
                break;
            default:
                throw new OAuth2AuthenticationProcessingException("Provider  invalid");
        }
        ModelMapper modelMapper = new ModelMapper();
        UserModel userModel;
        if (userEntity != null) {
            userModel = modelMapper.map(userEntity, UserModel.class);
            if (!userModel.getProvider().equals(oAuth2UserRequest.getClientRegistration().getRegistrationId())) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        userModel.getProvider() + " account. Please use your " + userModel.getProvider() + " account to login.");
            }
            updateExistingUser(userModel, oAuth2UserInfo, modelMapper);
        } else {
            userModel = registerNewUser(oAuth2UserRequest, oAuth2UserInfo, modelMapper);
        }

        return UserPrincipal.create(userModel, oAuth2User.getAttributes(), Arrays.stream(userModel.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }


    private UserModel registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo, ModelMapper modelMapper) {
        var users = new Users();
        users.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()).name());
        if (oAuth2UserRequest.getClientRegistration().getRegistrationId().equalsIgnoreCase(AuthProvider.google.name())) {
            users.setUserName(oAuth2UserInfo.getName());
        } else if (oAuth2UserRequest.getClientRegistration().getRegistrationId().equalsIgnoreCase(AuthProvider.facebook.name())) {
            users.setUserName(oAuth2UserInfo.getId());
        } else if (oAuth2UserRequest.getClientRegistration().getRegistrationId().equalsIgnoreCase(AuthProvider.github.name())) {
            users.setUserName(oAuth2UserInfo.getId());
        }
        users.setEmail(oAuth2UserInfo.getEmail());
        users.setProviderId(oAuth2UserInfo.getId());
        users.setImageUrl(StringUtils.isNotBlank(oAuth2UserInfo.getImageUrl()) ? oAuth2UserInfo.getImageUrl() : "");
        users.setPassword(new BCryptPasswordEncoder().encode(oAuth2UserInfo.getId()));
        users.setRoles(oAuth2UserRequest.getClientRegistration().getRegistrationId());
        users = usersRepository.save(users);
        saveUserStatus(users.getId());
        return modelMapper.map(users, UserModel.class);
    }

    private UserModel updateExistingUser(UserModel userModel, OAuth2UserInfo oAuth2UserInfo, ModelMapper modelMapper) {
        userModel.setUserName(oAuth2UserInfo.getName());
        userModel.setImageUrl(StringUtils.isNotBlank(oAuth2UserInfo.getImageUrl()) ? oAuth2UserInfo.getImageUrl() : "");
        Users users = modelMapper.map(userModel, Users.class);
        users = usersRepository.save(users);
        userModel.setId(users.getId());
        return userModel;
    }

    private void saveUserStatus(Long userId) {
        UserStatus userStatus = new UserStatus();
        userStatus.setStatus(Status.ONLINE.name());
        userStatus.setUserId(userId);
        userStatus.setLastTimeLogin(DateUtils.convertDateToString(new Date(), "dd-MM-yyyy HH:mm:ss"));
        userStatusRepository.save(userStatus);
    }

}
