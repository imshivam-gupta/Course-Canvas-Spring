package com.example.coursecanvasspring.entity.chat;

import com.example.coursecanvasspring.entity.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import static com.example.coursecanvasspring.constants.StringConstants.CHAT_COLLECTION;

@Getter
@Setter
@Document(collection = CHAT_COLLECTION)
public class ChatMessage {
    private String _id;
    private MessageType type;
    private String content;
    private String sender;

    @DBRef
    private User user;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}