package com.example.chatplatform.controller;

import com.example.chatplatform.model.ChatMessage;
import com.example.chatplatform.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessage chatMessage) {
        if (chatMessage.getContent() == null || chatMessage.getContent().isEmpty()) {
            return ResponseEntity.badRequest().body("Message content cannot be empty");
        }
        ChatMessage savedMessage = chatMessageService.saveMessage(chatMessage.getSender(), chatMessage.getContent(), chatMessage.getChatRoomId());
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/history/{chatRoomId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable Long chatRoomId) {
        List<ChatMessage> messages = chatMessageService.getMessagesByChatRoom(chatRoomId);
        return ResponseEntity.ok(messages);
    }
}