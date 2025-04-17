package com.example.chatplatform.service;

import com.example.chatplatform.dao.ChatRoomDAO;
import com.example.chatplatform.model.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {

    private final ChatRoomDAO chatRoomDAO;

    public ChatRoomService(ChatRoomDAO chatRoomDAO) {
        this.chatRoomDAO = chatRoomDAO;
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = new ChatRoom(name);
        chatRoomDAO.save(chatRoom);
        return chatRoom;
    }

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomDAO.findAll();
    }
}