package com.example.chatplatform.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatRoom {

    // Getters and Setters
    private Long id;
    private String name;

    // Constructors
    public ChatRoom() {}

    public ChatRoom(String name) {
        this.name = name;
    }

}
