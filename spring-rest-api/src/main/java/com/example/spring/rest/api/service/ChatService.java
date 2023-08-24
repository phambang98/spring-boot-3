package com.example.spring.rest.api.service;

import com.example.spring.rest.api.model.UserChatGroupModel;
import com.example.spring.rest.api.security.SecurityUtils;
import com.example.spring.rest.api.security.UserPrincipal;
import com.example.spring.rest.api.model.ChatGroupModel;
import com.example.core.entity.ChatGroup;
import com.example.core.enums.ChatType;
import com.example.core.enums.SocketType;
import com.example.core.enums.Status;
import com.example.core.error.BadRequestException;
import com.example.core.error.NewConversationException;
import com.example.core.error.ResourceNotFoundException;
import com.example.core.model.ChatModel;
import com.example.spring.rest.api.model.SocketModel;
import com.example.core.entity.Chat;
import com.example.core.repository.ChatGroupRepository;
import com.example.core.repository.ChatRepository;
import com.example.core.repository.UserStatusRepository;
import com.example.core.repository.UsersRepository;
import com.example.core.utils.WebSocketKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private ChatGroupRepository chatGroupRepository;

    public List<ChatModel> getFriendList(Long id) {
        return chatRepository.getFriendList(id);
    }


    public ChatModel newConversation(UserPrincipal userPrincipal, String userName) throws ResourceNotFoundException, NewConversationException {
        var friend = usersRepository.findByUserName(userName);
        if (friend == null) {
            throw new ResourceNotFoundException("User email-" + userName);
        }
        if (friend.getId().equals(userPrincipal.getId())) {
            throw new NewConversationException("Can't chat for myself-" + userName);
        }

        SocketType typeConv = SocketType.USER_CONVERSATION_UPDATED;
        var chatEntity = chatRepository.findByUserId1AndUserId2AndChatType(userPrincipal.getId(), friend.getId(), ChatType.NORMAL.getId());
        if (chatEntity == null) {
            chatEntity = new Chat();
            chatEntity.setUserId1(userPrincipal.getId());
            chatEntity.setUserId2(friend.getId());
            chatEntity.setChatType(ChatType.NORMAL.getId());
            chatEntity.setCreatedAt(new Date());
            chatEntity.setUpdatedAt(new Date());
            chatEntity = chatRepository.save(chatEntity);
            typeConv = SocketType.USER_CONVERSATION_ADDED;
        }
        var friendStatusEntity = userStatusRepository.findByUserId(friend.getId());
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(friend.getId()), WebSocketKey.DESTINATION_STATUS, new SocketModel<>(typeConv,
                new ChatModel(chatEntity.getChatId(), userPrincipal.getId(), userPrincipal.getName(),
                        userPrincipal.getImageUrl(), chatEntity.getBlockedBy(), Status.ONLINE.name(), null,
                        null, ChatType.NORMAL.getId())));
        return new ChatModel(chatEntity.getChatId(), friend.getId(), friend.getUserName(), friend.getImageUrl(), chatEntity.getBlockedBy(),
                friendStatusEntity.getStatus(), null, null, ChatType.NORMAL.getId());
    }

    public ChatModel blockChat(Long chatId, UserPrincipal user) throws ResourceNotFoundException, BadRequestException {
        var chatEntity = chatRepository.findByChatId(chatId);
        Date date = new Date();
        if (chatEntity != null) {
            if (chatEntity.getBlockedBy() == null) {
                chatEntity.setBlockedBy(user.getId());
                chatEntity.setUpdatedAt(new Date());
                chatEntity = chatRepository.save(chatEntity);
                Long friendId = chatEntity.getUserId1().equals(user.getId()) ? chatEntity.getUserId2() : chatEntity.getUserId1();
                var recipientUser = usersRepository.findById(friendId);
                if (recipientUser.isEmpty()) {
                    throw new ResourceNotFoundException("Data error");
                }
                simpMessagingTemplate.convertAndSendToUser(String.valueOf(recipientUser.get().getId()), WebSocketKey.DESTINATION_STATUS,
                        new SocketModel<>(SocketType.USER_CONVERSATION_BLOCK,
                                new ChatModel(chatEntity.getChatId(), user.getId(), user.getUsername(),
                                        user.getImageUrl(), chatEntity.getBlockedBy(), Status.OFFLINE.name(),
                                        null, null, ChatType.NORMAL.getId())));
                return new ChatModel(chatEntity.getChatId(), friendId, recipientUser.get().getUserName(),
                        recipientUser.get().getImageUrl(), chatEntity.getBlockedBy(), Status.OFFLINE.name(), null, null, ChatType.NORMAL.getId());
            } else {
                throw new BadRequestException("Sorry you can block this conversation");
            }
        } else {
            throw new ResourceNotFoundException("Conversation not found -recipientId:" + chatId);
        }
    }

    public ChatModel unBlockChat(Long chatId, UserPrincipal user) throws ResourceNotFoundException, BadRequestException {
        var chatEntity = chatRepository.findByChatId(chatId);
        if (chatEntity != null) {
            if (chatEntity.getBlockedBy().equals(user.getId())) {
                chatEntity.setBlockedBy(null);
                chatEntity.setUpdatedAt(new Date());
                chatEntity = chatRepository.save(chatEntity);
                Long friendId = chatEntity.getUserId1().equals(user.getId()) ? chatEntity.getUserId2() : chatEntity.getUserId1();
                var friend = usersRepository.findById(friendId);
                if (friend.isEmpty()) {
                    throw new ResourceNotFoundException("Data error");
                }
                var userStatus = userStatusRepository.findByUserId(friendId);
                simpMessagingTemplate.convertAndSendToUser(String.valueOf(friendId), WebSocketKey.DESTINATION_STATUS,
                        new SocketModel<>(SocketType.USER_CONVERSATION_UNBLOCK,
                                new ChatModel(chatEntity.getChatId(), user.getId(), user.getName(), user.getImageUrl(), chatEntity.getBlockedBy(),
                                        userStatus.getStatus(), null, null, ChatType.NORMAL.getId())));
                return new ChatModel(chatEntity.getChatId(), friendId, friend.get().getUserName(), friend.get().getImageUrl(), chatEntity.getBlockedBy(),
                        userStatus.getStatus(), null, null, ChatType.NORMAL.getId());
            }
            throw new BadRequestException("Sorry you can unblock this conversation -chatId: " + chatId);
        } else {
            throw new ResourceNotFoundException("Conversation not found -chatId:" + chatId);
        }
    }

    public void createGroupChat(UserPrincipal userPrincipal, ChatGroupModel chatGroupModel) throws ResourceNotFoundException {
        if (chatGroupModel.getListUserName().stream().anyMatch(x -> x.equals(userPrincipal.getUsername()))) {
            throw new ResourceNotFoundException("List user name error");
        }
        chatGroupModel.getListUserName().add(userPrincipal.getUsername());
        var listUsers = usersRepository.findByUserNameIn(chatGroupModel.getListUserName());
        if (listUsers.size() != chatGroupModel.getListUserName().size()) {
            throw new ResourceNotFoundException("List user name error");
        }
        var chatEntity = new Chat();
        Date date = new Date();
        chatEntity.setChatType(ChatType.GROUP.getId());
        chatEntity.setCreatedAt(date);
        chatEntity.setDisplayName(chatGroupModel.getNameChatGroup());
        chatEntity.setCreatedBy(userPrincipal.getId());
        chatEntity = chatRepository.save(chatEntity);
        Chat finalChatEntity = chatEntity;
        List<ChatGroup> chatGroupList = listUsers.stream().map(x -> new ChatGroup(finalChatEntity.getChatId(), x.getId(), date)).collect(Collectors.toList());
        chatGroupRepository.saveAll(chatGroupList);
        for (var user : listUsers) {
            simpMessagingTemplate.convertAndSendToUser(String.valueOf(user.getId()), WebSocketKey.DESTINATION_STATUS, new SocketModel<>(SocketType.USER_CONVERSATION_ADDED,
                    new ChatModel(chatEntity.getChatId(), null, chatEntity.getDisplayName(),
                            chatEntity.getImageUrl(), chatEntity.getBlockedBy(), Status.ONLINE.name(), null, null, ChatType.GROUP.getId())));
        }
    }

    public void addUserGroupChat(UserChatGroupModel userChatGroupModel) throws ResourceNotFoundException {
        UserPrincipal userPrincipal = SecurityUtils.getCurrentIdLogin();
        if (userChatGroupModel.getListUserName().stream().anyMatch(x -> x.equals(userPrincipal.getUsername()))) {
            throw new ResourceNotFoundException("List user name error");
        }
        var listUsers = usersRepository.findByUserNameIn(userChatGroupModel.getListUserName());
        if (listUsers.size() != userChatGroupModel.getListUserName().size()) {
            throw new ResourceNotFoundException("List user name error");
        }
        var chatEntity = chatRepository.findByChatId(userChatGroupModel.getChatId());
        if (chatEntity == null) {
            throw new ResourceNotFoundException("Chat Id does not exist");
        }
        Date date = new Date();
        List<ChatGroup> chatGroupList = listUsers.stream().map(x -> new ChatGroup(chatEntity.getChatId(), x.getId(), date)).collect(Collectors.toList());
        chatGroupRepository.saveAllAndFlush(chatGroupList);
        for (var userId : chatGroupRepository.getUserIdByChatId(chatEntity.getChatId())) {
            simpMessagingTemplate.convertAndSendToUser(String.valueOf(userId), WebSocketKey.DESTINATION_STATUS, new SocketModel<>(SocketType.USER_CONVERSATION_GROUP_ADDED,
                    new ChatModel(chatEntity.getChatId(), null, chatEntity.getDisplayName(),
                            chatEntity.getImageUrl(), chatEntity.getBlockedBy(), Status.ONLINE.name(), null, null, ChatType.GROUP.getId())));
        }
    }

    public void removeUserFromGroupChat(UserChatGroupModel userChatGroupModel) throws ResourceNotFoundException {
        var chatEntity = chatRepository.findByChatId(userChatGroupModel.getChatId());
        if (chatEntity == null) {
            throw new ResourceNotFoundException("Chat Id does not exist");
        }
        var listUsers = usersRepository.findByUserNameIn(userChatGroupModel.getListUserName());
        if (listUsers.size() != userChatGroupModel.getListUserName().size()) {
            throw new ResourceNotFoundException("List user name error");
        }
        int countDelete = chatGroupRepository.deleteByChatIdAndUserNameIn(userChatGroupModel.getChatId(), userChatGroupModel.getListUserName());
        if (countDelete == 0) {
            throw new ResourceNotFoundException("Chat Id And User Name does not exist");
        }
        for (var userId : chatGroupRepository.getUserIdByChatId(chatEntity.getChatId())) {
            simpMessagingTemplate.convertAndSendToUser(String.valueOf(userId), WebSocketKey.DESTINATION_STATUS, new SocketModel<>(SocketType.USER_CONVERSATION_GROUP_REMOVE_USER,
                    new ChatModel(chatEntity.getChatId(), null, chatEntity.getDisplayName(),
                            chatEntity.getImageUrl(), chatEntity.getBlockedBy(), Status.ONLINE.name(), null, null, ChatType.GROUP.getId())));
        }

    }

    public void leaveGroupChat(Long chatId) throws ResourceNotFoundException {
        UserPrincipal userPrincipal = SecurityUtils.getCurrentIdLogin();
        var chatEntity = chatRepository.findByChatId(chatId);
        if (chatEntity == null) {
            throw new ResourceNotFoundException("Chat Id does not exist");
        }
        int countDelete = chatGroupRepository.deleteByChatIdAndUserId(chatId, userPrincipal.getId());
        chatGroupRepository.flush();
        if (countDelete == 0) {
            throw new ResourceNotFoundException("Chat Id And User Id does not exist");
        }

        for (var userId : chatGroupRepository.getUserIdByChatId(chatEntity.getChatId())) {
            simpMessagingTemplate.convertAndSendToUser(String.valueOf(userId), WebSocketKey.DESTINATION_STATUS, new SocketModel<>(SocketType.USER_CONVERSATION_GROUP_LEAVE,
                    new ChatModel(chatEntity.getChatId(), null, chatEntity.getDisplayName(),
                            chatEntity.getImageUrl(), chatEntity.getBlockedBy(), Status.ONLINE.name(),
                            null, null, ChatType.GROUP.getId())));
        }
    }
}

