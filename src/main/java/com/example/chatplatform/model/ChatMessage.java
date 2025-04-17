package com.example.chatplatform.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ChatMessage {

    // Getters and Setters
    private Long id;
    private String sender;
    private String content;
    private LocalDateTime timestamp;
    private Long chatRoomId; // Foreign key reference to ChatRoom

    // Constructors
    public ChatMessage() {}

    public ChatMessage(String sender, String content, LocalDateTime timestamp, Long chatRoomId) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.chatRoomId = chatRoomId;
    }

}
