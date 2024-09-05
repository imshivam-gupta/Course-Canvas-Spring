package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserSocketController {

    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public User addUser(@Payload User user) {
        chatMessageService.connect(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public User disconnectUser(@Payload User user) {
        chatMessageService.disconnect(user);
        return user;
    }

    @GetMapping("/connectedUsers")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return ResponseEntity.ok(chatMessageService.findConnectedUsers());
    }
}
