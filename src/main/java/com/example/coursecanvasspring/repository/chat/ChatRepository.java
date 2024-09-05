package com.example.coursecanvasspring.repository.chat;

import com.example.coursecanvasspring.entity.chat.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatMessage, String> {
    public List<ChatMessage> findAllBySender(String userId);
}

