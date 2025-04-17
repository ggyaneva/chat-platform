package com.example.chatplatform.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessage {

    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String sender;

    @NotNull
    @Size(min = 1, max = 500)
    private String content;

    @NotNull
    private LocalDateTime timestamp;

    @NotNull
    private Long chatRoomId;
}