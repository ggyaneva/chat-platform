package com.example.chatplatform.controller;

import com.example.chatplatform.model.ChatRoom;
import com.example.chatplatform.model.Message;
import com.example.chatplatform.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {

    private final ChatService chatService;

    public ChatRoomController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public List<ChatRoom> getChatRooms() {
        return chatService.getAllChatRooms();
    }

    @GetMapping("/{roomId}/messages")
    public List<Message> getMessages(@PathVariable Long roomId) {
        return chatService.getMessagesByChatRoomId(roomId);
    }

    @PostMapping("/{roomId}/messages")
    public void sendMessage(@PathVariable Long roomId, @RequestBody Message message) {
        chatService.sendMessage(roomId, message);
    }
}