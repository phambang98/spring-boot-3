package com.example.spring.rest.api.ultis;

import com.example.spring.rest.api.security.UserPrincipal;
import com.example.spring.rest.api.security.jwt.TokenProvider;
import com.example.spring.rest.api.service.UsersService;
import com.example.core.error.ResourceNotFoundException;
import com.example.core.utils.WebSocketKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public class CustomChannelInterceptor implements ChannelInterceptor {


    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UsersService userService;

    @Override
    public Message<?> preSend(Message message, MessageChannel channel) {
        var accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null) {
            throw new ResourceNotFoundException("Accessor not found");
        }
        if (StompCommand.CONNECT == accessor.getCommand()) {
            var authorization = accessor.getNativeHeader("Authorization");
            if (authorization == null) {
                throw new ResourceNotFoundException("Authorization not found");
            }
            var accessToken = getJwtFromRequest(authorization.get(0));

            if (StringUtils.isNotEmpty(accessToken) && Boolean.TRUE.equals(tokenProvider.validateToken(accessToken))) {
                Long userId = tokenProvider.getUserIdFromToken(accessToken);
                try {
                    UserDetails userDetails = userService.getUserById(userId);
                    var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    accessor.setUser(authentication);
                } catch (ResourceNotFoundException e) {
                    throw new ResourceNotFoundException(e);
                }
            }
        }
        if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            try {
                Long dst = getDst(accessor.getDestination(), "/notifications/", WebSocketKey.DESTINATION_MESSAGE);
                if (dst == null) {
                    dst = getDst(accessor.getDestination(), "/notifications/", WebSocketKey.DESTINATION_STATUS);
                }
                if (StringUtils.equals(accessor.getDestination(), WebSocketKey.LUCKY_WHEEL)) {
                    return message;
                }
                if (accessor.getUser() != null && Boolean.FALSE.equals(verifyUser(accessor.getUser(), dst))) {
                    return null;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (StompCommand.SEND == accessor.getCommand()) {

        }
        return message;
    }

    private String getJwtFromRequest(String bearerToken) {
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    private Long getDst(String s, String startPrefix, String endPrefix) {
        if (StringUtils.isNotBlank(s) && s.startsWith(startPrefix) && s.endsWith(endPrefix)) {
            s = s.substring(startPrefix.length(), s.length());
            return Long.valueOf(s.substring(0, s.length() - endPrefix.length()));
        }
        return null;
    }

    private Boolean verifyUser(Object user, Long id) {
        if (user instanceof UsernamePasswordAuthenticationToken authenticationToken) {
            UserPrincipal userPrincipal = (UserPrincipal) authenticationToken.getPrincipal();
            return userPrincipal.getId().equals(id);
        }
        return false;
    }
}
