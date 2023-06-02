package com.example.springchat.controller;

import com.example.springchat.error.BadRequestException;
import com.example.springchat.error.NewConversationException;
import com.example.springchat.error.ResourceNotFoundException;
import com.example.springchat.model.ChatGroupModel;
import com.example.springchat.security.SecurityUtils;
import com.example.springchat.security.UserPrincipal;
import com.example.springchat.service.ChatService;
import com.example.springcore.model.ChatModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("chat")
    public ResponseEntity<List<ChatModel>> getFriendList() {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return ResponseEntity.ok(chatService.getFriendList(user.getId()));
    }

    @PostMapping("chat")
    public ResponseEntity<ChatModel> newConversation(@RequestParam(value = "userName") String userName) throws ResourceNotFoundException, NewConversationException {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return ResponseEntity.ok(chatService.newConversation(user, userName));
    }

    @PostMapping("chat/block/{chatId}")
    public ResponseEntity<ChatModel> blockChat(@PathVariable("chatId") Long chatId) throws BadRequestException, ResourceNotFoundException {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return ResponseEntity.ok(chatService.blockChat(chatId, user));
    }

    @PostMapping("chat/unblock/{chatId}")
    public ResponseEntity<ChatModel> unBlockChat(@PathVariable("chatId") Long chatId) throws BadRequestException, ResourceNotFoundException {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return ResponseEntity.ok(chatService.unBlockChat(chatId, user));
    }

    @PostMapping("chat/group-new")
    public ResponseEntity<Void> newGroupChat(@Valid @RequestBody ChatGroupModel chatGroupModel) throws ResourceNotFoundException, NewConversationException {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        chatService.newGroupChat(user, chatGroupModel);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("chat/group-add")
//    public ResponseEntity<ChatModel> addGroupChat(@Valid @RequestBody ChatGroupModel chatGroupModel) throws ResourceNotFoundException, NewConversationException {
//        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
//        return ResponseEntity.ok(chatService.newGroupChat(user, chatGroupModel));
//    }

    @DeleteMapping("chat/group")
    public ResponseEntity<ChatModel> removeGroupChat(@RequestParam(value = "userName") String userName) throws ResourceNotFoundException, NewConversationException {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return ResponseEntity.ok(chatService.removeGroupChat(user, userName));
    }


}
