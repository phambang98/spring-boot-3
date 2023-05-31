package com.example.springchat.controller;

import com.example.springchat.error.BadRequestException;
import com.example.springchat.error.NewConversationException;
import com.example.springchat.error.ResourceNotFoundException;
import com.example.springchat.security.SecurityUtils;
import com.example.springchat.security.UserPrincipal;
import com.example.springchat.service.ChatService;
import com.example.springcore.model.FriendProfileModel;
import com.example.springcore.model.StatusModel;
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
    public ResponseEntity<List<FriendProfileModel>> getFriendList() {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return ResponseEntity.ok(chatService.getFriendList(user.getId()));
    }

    @PostMapping("chat")
    public ResponseEntity<FriendProfileModel> newConversation(@RequestParam(value = "userName") String userName) throws ResourceNotFoundException, NewConversationException {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return ResponseEntity.ok(chatService.newConversation(user, userName));
    }

    @PostMapping("chat/block/{recipientId}")
    public ResponseEntity<FriendProfileModel> blockUser(@PathVariable("recipientId") Long recipientId) throws BadRequestException, ResourceNotFoundException {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return ResponseEntity.ok(chatService.blockConversation(recipientId, user));
    }

    @PostMapping("chat/unblock/{recipientId}")
    public ResponseEntity<FriendProfileModel> unblockUser(@PathVariable("recipientId") Long recipientId) throws BadRequestException, ResourceNotFoundException {
        UserPrincipal user = SecurityUtils.getCurrentIdLogin();
        return ResponseEntity.ok(chatService.unblockConversation(recipientId, user));
    }

    @GetMapping("status/{userId}")
    public ResponseEntity<List<StatusModel>> getFriendStatusByUserId(@PathVariable("userId")Long userId) {
        return ResponseEntity.ok(chatService.getFriendStatusByUserId(userId));
    }

}
