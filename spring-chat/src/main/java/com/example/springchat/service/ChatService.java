package com.example.springchat.service;

import com.example.springchat.enums.SocketType;
import com.example.springchat.error.BadRequestException;
import com.example.springchat.error.NewConversationException;
import com.example.springchat.error.ResourceNotFoundException;
import com.example.springcore.enums.Status;
import com.example.springcore.model.FriendProfileModel;
import com.example.springchat.model.SocketModel;
import com.example.springchat.security.UserPrincipal;
import com.example.springcore.entity.Chat;
import com.example.springcore.model.StatusModel;
import com.example.springcore.repository.ChatRepository;
import com.example.springcore.repository.UserStatusRepository;
import com.example.springcore.repository.UsersRepository;
import com.example.springcore.utils.WebSocketKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserStatusRepository userStatusRepository;

    public List<FriendProfileModel> getFriendList(Long id) {
        return chatRepository.getFriendList(id);
    }


    @Transactional
    public FriendProfileModel newConversation(UserPrincipal userPrincipal, String userName) throws ResourceNotFoundException, NewConversationException {
        var friend = usersRepository.findByUserName(userName);
        if (friend == null) {
            throw new ResourceNotFoundException("User email-" + userName);
        }
        if (friend.getId().equals(userPrincipal.getId())) {
            throw new NewConversationException("Can't chat for myself-" + userName);
        }

        SocketType typeConv = SocketType.USER_CONVERSATION_UPDATED;
        var chatEntity = chatRepository.findByUserId1AndUserId2OrUserId1AndUserId2(userPrincipal.getId(), friend.getId(), friend.getId(), userPrincipal.getId());
        if (chatEntity == null) {
            chatEntity = new Chat(null, userPrincipal.getId(), friend.getId(), null, new Date(), new Date());
            chatEntity = chatRepository.save(chatEntity);
            typeConv = SocketType.USER_CONVERSATION_ADDED;
        }
        var userStatusEntity = userStatusRepository.findByUserId(userPrincipal.getId());
        var friendStatusEntity = userStatusRepository.findByUserId(friend.getId());
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(friend.getId()), WebSocketKey.DESTINATION_STATUS, new SocketModel<>(typeConv,
                new FriendProfileModel(userPrincipal.getId(), userPrincipal.getEmail(), userPrincipal.getName(),
                        userPrincipal.getImageUrl(), chatEntity.getBlockedBy(), userStatusEntity.getStatus(), userStatusEntity.getLastTimeLogin(),  "", null)));
        return new FriendProfileModel(friend.getId(), friend.getEmail(), friend.getUserName(), friend.getImageUrl(), chatEntity.getBlockedBy(),
                friendStatusEntity.getStatus(), friendStatusEntity.getLastTimeLogin(), "", null);
    }

    public FriendProfileModel blockConversation(Long recipientId, UserPrincipal user) throws ResourceNotFoundException, BadRequestException {
        var chatEntity = chatRepository.findByUserId1AndUserId2OrUserId1AndUserId2(recipientId, user.getId(), user.getId(), recipientId);
        if (chatEntity != null) {
            if (chatEntity.getBlockedBy() == null) {
                chatEntity.setBlockedBy(user.getId());
                chatEntity.setUpdatedAt(new Date());
                chatEntity = chatRepository.save(chatEntity);
                var recipientUser = usersRepository.findById(recipientId);
                if (recipientUser.isEmpty()) {
                    throw new ResourceNotFoundException("Data error");
                }
                simpMessagingTemplate.convertAndSendToUser(String.valueOf(recipientUser.get().getId()), WebSocketKey.DESTINATION_STATUS,
                        new SocketModel<>(SocketType.USER_CONVERSATION_UPDATED,
                                new FriendProfileModel(recipientId, user.getEmail(), user.getUsername(),
                                        user.getImageUrl(), chatEntity.getBlockedBy(), Status.OFFLINE.name(), "", "", null)));
                return new FriendProfileModel(recipientId, recipientUser.get().getEmail(), recipientUser.get().getUserName(),
                        recipientUser.get().getImageUrl(), chatEntity.getBlockedBy(), Status.OFFLINE.name(), "", "", null);
            } else {
                throw new BadRequestException("Sorry you can block this conversation");
            }
        } else {
            throw new ResourceNotFoundException("Conversation not found -recipientId:" + recipientId);
        }
    }

    public FriendProfileModel unblockConversation(Long recipientId, UserPrincipal user) throws ResourceNotFoundException, BadRequestException {
        var chatEntity = chatRepository.findByUserId1AndUserId2OrUserId1AndUserId2(recipientId, user.getId(), user.getId(), recipientId);
        if (chatEntity != null) {
            if (chatEntity.getBlockedBy().equals(user.getId())) {
                chatEntity.setBlockedBy(null);
                chatEntity.setUpdatedAt(new Date());
                chatEntity = chatRepository.save(chatEntity);
                var friend = usersRepository.findById(recipientId);
                if (friend.isEmpty()) {
                    throw new ResourceNotFoundException("Data error");
                }
                var userStatus = userStatusRepository.findByUserId(recipientId);
                simpMessagingTemplate.convertAndSendToUser(String.valueOf(recipientId), WebSocketKey.DESTINATION_MESSAGE,
                        new SocketModel<>(SocketType.USER_CONVERSATION_UPDATED,
                                new FriendProfileModel(recipientId, user.getEmail(), user.getName(), user.getImageUrl(), chatEntity.getBlockedBy(),
                                        userStatus.getStatus(), userStatus.getLastTimeLogin(), "", null)));
                return new FriendProfileModel(recipientId, friend.get().getEmail(), friend.get().getUserName(), friend.get().getImageUrl(), chatEntity.getBlockedBy(),
                        userStatus.getStatus(), userStatus.getLastTimeLogin(), "", null);
            }
            throw new BadRequestException("Sorry you can unblock this conversation -recipientId: " + recipientId);
        } else {
            throw new ResourceNotFoundException("Conversation not found -recipientId:" + recipientId);
        }
    }

    public List<StatusModel> getFriendStatusByUserId(Long userId) {
        return chatRepository.getFriendStatusByUserId(userId);
    }
}

