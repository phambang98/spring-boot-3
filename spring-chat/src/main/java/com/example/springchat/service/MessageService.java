package com.example.springchat.service;

import com.example.springcore.enums.ChatType;
import com.example.springchat.enums.ContentType;
import com.example.springchat.enums.SocketType;
import com.example.springchat.error.BadRequestException;
import com.example.springchat.error.ResourceNotFoundException;
import com.example.springchat.model.DeleteMessageModel;
import com.example.springcore.entity.*;
import com.example.springcore.model.FileModel;
import com.example.springcore.model.MessageModel;
import com.example.springchat.model.MessageRequest;
import com.example.springchat.model.SocketModel;
import com.example.springchat.security.UserPrincipal;
import com.example.springcore.repository.*;
import com.example.springcore.utils.CommonKey;
import com.example.springcore.utils.WebSocketKey;
import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserStatusRepository userStatusRepository;


    public List<MessageModel> getMessagesByRecipientId(Long chatId, Long userId) {
        ModelMapper modelMapper = new ModelMapper();
        Chat chatEntity = chatRepository.findByChatId(chatId);
        if (chatEntity != null) {
            List<MessageModel> messageModelList = modelMapper.map(messageRepository.findAllByChatId(chatEntity.getChatId()), new TypeToken<List<MessageModel>>() {
            }.getType());
            messageModelList.forEach(x -> {
                x.setImageUrl(usersRepository.findById(x.getSenderId()).orElseThrow().getImageUrl());
                if (chatEntity.getChatType().equals(ChatType.NORMAL.getId())) {
                    Long recipientId = chatEntity.getUserId1().equals(userId) ? chatEntity.getUserId2() : chatEntity.getUserId1();
                    if (!x.getSenderId().equals(recipientId)) {
                        x.setRecipientId(recipientId);
                    }
                }
                List<FileModel> fileModelList = fileRepository.findByMessageId(x.getMessageId()).stream()
                        .map(f -> new FileModel(CommonKey.API_GET_FILE_CHAT + f.getFileName(), f.getType(), f.getFileName()))
                        .collect(Collectors.toList());
                x.setFiles(fileModelList);
            });
            return messageModelList;
        }
        return new ArrayList<>();
    }

    @Transactional
    public void deleteMessage(Long messageId, Long userId) {
        Message messageEntity = messageRepository.findByMessageId(messageId);
        if (messageEntity != null) {
            Chat chatEntity = chatRepository.findByChatId(messageEntity.getChatId());
            if (chatEntity != null) {
                Long friendId = chatEntity.getUserId1().equals(userId) ? chatEntity.getUserId2() : chatEntity.getUserId1();
                messageRepository.deleteById(messageId);
                simpMessagingTemplate.convertAndSendToUser(
                        String.valueOf(friendId), WebSocketKey.DESTINATION_MESSAGE, new SocketModel<>(SocketType.USER_MESSAGE_DELETE, new DeleteMessageModel(userId, messageId)));
            } else {
                throw new ResourceNotFoundException("Data Error -chatId:" + messageEntity.getChatId());
            }
        } else {
            throw new ResourceNotFoundException("Message Not Found -messageId:" + messageId);
        }
    }

    @Transactional
    public void createMessage(MessageRequest messageRequest, UserPrincipal user) throws BadRequestException, ResourceNotFoundException {
        Chat chat = chatRepository.findByChatId(messageRequest.getChatId());
        if (chat != null) {
            if (chat.getBlockedBy() == null) {
                ModelMapper modelMapper = new ModelMapper();
                Message messageEntity;
                if (messageRequest.getMessageId() != null) {
                    messageEntity = messageRepository.findByMessageId(messageRequest.getMessageId());
                    if (messageEntity == null) {
                        throw new ResourceNotFoundException("Message -id:" + messageRequest.getMessageId());
                    }
                    if (Boolean.FALSE.equals(messageRequest.getUpdateMessage())) {
                        messageEntity.setContentType(ContentType.file.name());
                    }
                } else {
                    messageEntity = new Message();
                    messageEntity.setContentType(ContentType.text.name());
                    messageEntity.setSenderId(user.getId());
                    messageEntity.setRecipientId(messageRequest.getRecipientId());
                    messageEntity.setChatId(chat.getChatId());
                }
                messageEntity.setContent(messageRequest.getContent());
                if (Boolean.FALSE.equals(messageRequest.getUpdateMessage())) {
                    messageEntity.setCreatedAt(new Date());
                } else {
                    messageEntity.setUpdatedAt(new Date());
                }
                var messageModel = modelMapper.map(messageRepository.save(messageEntity), MessageModel.class);
                messageModel.setSenderName(user.getUsername());
                messageModel.setImageUrl(user.getImageUrl());
                messageModel.setRecipientId(messageRequest.getRecipientId());
                if (CollectionUtils.isNotEmpty(messageModel.getFiles())) {
                    messageModel.getFiles().forEach(x -> {
                        x.setUrl(CommonKey.API_GET_FILE_CHAT + x.getFileName());
                    });
                }
                if (chat.getChatType().equals(ChatType.GROUP.getId())) {
                    for (var friendId : chatGroupRepository.getUserIdByChatId(messageModel.getChatId())) {
                        simpMessagingTemplate.convertAndSendToUser(String.valueOf(friendId), WebSocketKey.DESTINATION_MESSAGE,
                                new SocketModel<>(SocketType.USER_MESSAGE_GROUP_ADDED, messageModel));
                    }
                } else {
                    simpMessagingTemplate.convertAndSendToUser(String.valueOf(messageRequest.getRecipientId()), WebSocketKey.DESTINATION_MESSAGE, new SocketModel<>(SocketType.USER_MESSAGE_ADDED, messageModel));
                    simpMessagingTemplate.convertAndSendToUser(String.valueOf(user.getId()), WebSocketKey.DESTINATION_MESSAGE, new SocketModel<>(SocketType.USER_MESSAGE_ADDED, messageModel));
                }
            } else {
                throw new BadRequestException("Sorry you're blocked by user");
            }
        } else {
            throw new ResourceNotFoundException("Conversation -id:" + messageRequest.getRecipientId());
        }
    }

    @Transactional
    public MessageModel createMessageFile(Long recipientId, String chatType, List<MultipartFile> multipartFileList, UserPrincipal user) throws BadRequestException, ResourceNotFoundException {
        Chat chat = chatRepository.findByUserId1AndUserId2AndChatType(user.getId(), null, chatType);
        if (chat != null) {
            if (chat.getBlockedBy() == null) {
                ModelMapper modelMapper = new ModelMapper();
                Message message = new Message();
                message.setContentType(ContentType.file.name());
                message.setSenderId(user.getId());
                message.setChatId(chat.getChatId());
                message.setRecipientId(recipientId);
                var messageModel = modelMapper.map(messageRepository.save(message), MessageModel.class);
                var uploadedFiles = fileStorageService.store(multipartFileList, messageModel.getChatId());
                List<File> entityList = modelMapper.map(uploadedFiles, new TypeToken<List<File>>() {
                }.getType());
                entityList.forEach(x -> x.setMessageId(messageModel.getMessageId()));
                fileRepository.saveAll(entityList);
                messageModel.setSenderName(user.getUsername());
                messageModel.setRecipientId(recipientId);
                messageModel.setImageUrl(user.getImageUrl());
                messageModel.setFiles(modelMapper.map(fileRepository.saveAll(entityList), new TypeToken<List<FileModel>>() {
                }.getType()));
                return messageModel;
            } else {
                throw new BadRequestException("Sorry you're blocked by user");
            }
        } else {
            throw new ResourceNotFoundException("Conversation -recipientId:" + recipientId);
        }
    }

}
