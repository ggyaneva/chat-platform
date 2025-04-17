package com.example.chatplatform.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;
}