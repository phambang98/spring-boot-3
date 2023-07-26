package com.example.spring.rest.api.listener;

import com.example.spring.rest.api.security.UserPrincipal;
import com.example.spring.rest.api.model.SocketModel;
import com.example.core.entity.Chat;
import com.example.core.enums.SocketType;
import com.example.core.enums.Status;
import com.example.core.model.StatusModel;
import com.example.core.repository.ChatRepository;
import com.example.core.repository.UserStatusRepository;
import com.example.core.utils.DateUtils;
import com.example.core.utils.WebSocketKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

import java.util.Date;
import java.util.List;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @EventListener
    public void handleWebSocketSessionConnectEvent(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        if (headerAccessor.getSessionAttributes() != null && event.getUser() instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
            headerAccessor.getSessionAttributes().put("username", usernamePasswordAuthenticationToken.getPrincipal());
            UserPrincipal userPrincipal = (UserPrincipal) usernamePasswordAuthenticationToken.getPrincipal();
            if (userPrincipal != null) {
                processSession(userPrincipal.getId(), userPrincipal.getUsername(), Status.ONLINE.name());
            }
        }
    }


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("SessionConnectedEvent");
    }

    @EventListener
    public void handleWebSocketSessionSubscribeEvent(SessionSubscribeEvent event) {
        logger.info("SessionSubscribeEvent");
    }

    @EventListener
    public void handleWebSocketSessionSubscribeEvent(SessionUnsubscribeEvent event) {
        logger.info("SessionUnsubscribeEvent");
    }


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        if (headerAccessor.getSessionAttributes() != null && headerAccessor.getSessionAttributes().get("username") instanceof UserPrincipal userPrincipal) {
            logger.info("User Disconnected :{} ", userPrincipal.getUsername());
            processSession(userPrincipal.getId(), userPrincipal.getUsername(), Status.OFFLINE.name());
        }

    }

    public void processSession(Long userId, String userName, String status) {
        var simpUser = simpUserRegistry.getUser(userName);
        if (simpUser == null) {
            synchronized (updateStatusUsers(userId, userName, status)) {
                logger.info("done");
            }
        }
    }

    private Object updateStatusUsers(Long userId, String userName, String status) {
        userStatusRepository.updateStatusAndTimeByUserName(status, DateUtils.convertDateToString(new Date(), "yyyy-MM-dd hh:mm:ss"), userId);
        List<Chat> chatList = chatRepository.findByUserId(userId);
        String dateTime = DateUtils.convertDateToString(new Date(), "yyyy-MM-dd hh:mm:ss");
        StatusModel statusModel = new StatusModel();
        statusModel.setUserId(userId);
        statusModel.setUserName(userName);
        statusModel.setStatus(status);
        statusModel.setLastTimeLogin(dateTime);
        for (Chat chat : chatList) {
            var recipientId = chat.getUserId1().equals(userId) ? chat.getUserId2() : chat.getUserId1();
            statusModel.setChatId(chat.getChatId());
            messagingTemplate.convertAndSendToUser(String.valueOf(recipientId), WebSocketKey.DESTINATION_STATUS, new SocketModel<>(SocketType.USER_STATUS, statusModel));
        }
        return statusModel;
    }
}