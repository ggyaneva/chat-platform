package com.example.chatplatform.controller;

import com.example.chatplatform.model.ChatMessage;
import com.example.chatplatform.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody ChatMessage chatMessage) {
        return chatMessageService.saveMessage(chatMessage.getSender(), chatMessage.getContent(), chatMessage.getChatRoomId());
    }

    @GetMapping("/history/{chatRoomId}")
    public List<ChatMessage> getChatHistory(@PathVariable Long chatRoomId) {
        return chatMessageService.getMessagesByChatRoom(chatRoomId);
    }
}