package com.example.chatplatform.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class Message {

    // Getters and Setters
    private Long id;
    private String content;
    private ChatRoom chatRoom;
    private User sender;

}