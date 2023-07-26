package com.example.spring.rest.api.controller;

import com.example.spring.rest.api.model.MessageRequest;
import com.example.spring.rest.api.security.SecurityUtils;
import com.example.spring.rest.api.security.UserPrincipal;
import com.example.spring.rest.api.service.MessageService;
import com.example.core.error.BadRequestException;
import com.example.core.error.ResourceNotFoundException;
import com.example.core.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/message/{chatId}")
    public ResponseEntity<List<MessageModel>> getMessagesByRecipientId(@PathVariable("chatId") Long chatId) {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return ResponseEntity.ok(messageService.getMessagesByRecipientId(chatId, user.getId()));
    }

    @DeleteMapping("/message/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("messageId") Long messageId) {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        messageService.deleteMessage(messageId, user.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/message/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MessageModel> createMessageFile(@RequestParam(value = "recipientId") Long recipientId,
                                                          @RequestParam(value = "chatType") String chatType,
                                                          @RequestParam(value = "files") List<MultipartFile> files) throws ResourceNotFoundException, BadRequestException {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return ResponseEntity.ok(messageService.createMessageFile(recipientId, chatType, files, user));
    }

    @MessageMapping("message")
    public void processMessage(@Payload MessageRequest messageRequest) throws BadRequestException, ResourceNotFoundException {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        messageService.createMessage(messageRequest, user);
    }
}