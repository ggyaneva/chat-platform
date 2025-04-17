package com.example.chatplatform.service;

import com.example.chatplatform.dao.ChatRoomDAO;
import com.example.chatplatform.dao.MessageDAO;
import com.example.chatplatform.model.ChatRoom;
import com.example.chatplatform.model.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRoomDAO chatRoomDAO;
    private final MessageDAO messageDAO;

    public ChatService(ChatRoomDAO chatRoomDAO, MessageDAO messageDAO) {
        this.chatRoomDAO = chatRoomDAO;
        this.messageDAO = messageDAO;
    }

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomDAO.getAllChatRooms();
    }

    public List<Message> getMessagesByChatRoomId(Long chatRoomId) {
        return messageDAO.getMessagesByChatRoomId(chatRoomId);
    }

    public void sendMessage(Long chatRoomId, Message message) {
        messageDAO.addMessage(chatRoomId, message.getContent(), message.getSender().getId());
    }
}