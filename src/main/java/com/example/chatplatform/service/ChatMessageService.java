package com.example.chatplatform.service;

import com.example.chatplatform.dao.ChatMessageDAO;
import com.example.chatplatform.model.ChatMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageService {

    private final ChatMessageDAO chatMessageDAO;

    public ChatMessageService(ChatMessageDAO chatMessageDAO) {
        this.chatMessageDAO = chatMessageDAO;
    }

    public ChatMessage saveMessage(String sender, String content, Long chatRoomId) {
        ChatMessage message = new ChatMessage(sender, content, LocalDateTime.now(), chatRoomId);
        chatMessageDAO.save(message);
        return message;
    }

    public List<ChatMessage> getMessagesByChatRoom(Long chatRoomId) {
        return chatMessageDAO.findByChatRoomId(chatRoomId);
    }
}
