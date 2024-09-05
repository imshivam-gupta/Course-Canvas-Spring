package com.example.coursecanvasspring.service;

import com.example.coursecanvasspring.entity.chat.ChatMessage;
import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.enums.Status;
import com.example.coursecanvasspring.repository.chat.ChatRepository;
import com.example.coursecanvasspring.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatMessageService {
    @Autowired
    private ChatRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getChatHistory(String userId) {
        return chatMessageRepository.findAllBySender(userId);
    }

    public void connect(User user) {
        var storedUser = userRepository.findById(user.get_id()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.ONLINE);
            userRepository.save(storedUser);
        }
    }

    public void disconnect(User user) {
        var storedUser = userRepository.findById(user.get_id()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    public List<User> findConnectedUsers() {
        return userRepository.findAllByStatus(Status.ONLINE);
    }
}
