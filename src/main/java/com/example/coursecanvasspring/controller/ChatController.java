package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.entity.chat.ChatMessage;
import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.repository.user.UserRepository;
import com.example.coursecanvasspring.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/queue/reply")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        User user = userRepository.findById(chatMessage.getSender()).get();
        chatMessage.setUser(user);
        chatMessageService.saveMessage(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/queue/reply")
    public ChatMessage addUser(ChatMessage chatMessage) {
        chatMessage.setContent(chatMessage.getSender() + " joined the chat");
        messagingTemplate.convertAndSendToUser(chatMessage.getSender(), "/queue/history", chatMessageService.getChatHistory(chatMessage.getSender()));
        return chatMessage;
    }

    @GetMapping("/connectedUsers")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return ResponseEntity.ok(chatMessageService.findConnectedUsers());
    }
}
