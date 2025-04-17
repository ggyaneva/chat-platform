package com.example.chatplatform.controller;

import com.example.chatplatform.model.ChatRoom;
import com.example.chatplatform.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Autowired
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChatRoom(@RequestBody ChatRoom chatRoom) {
        if (chatRoom.getName() == null || chatRoom.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Chat room name cannot be empty");
        }
        ChatRoom createdRoom = chatRoomService.createChatRoom(chatRoom.getName());
        return ResponseEntity.ok(createdRoom);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoom>> getAllChatRooms() {
        List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
        return ResponseEntity.ok(chatRooms);
    }
}