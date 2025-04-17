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
        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setChatRoomId(chatRoomId);
        boolean success = chatMessageDAO.save(message);
        return success ? message : null;
    }

    public List<ChatMessage> getMessagesByChatRoom(Long chatRoomId) {
        return chatMessageDAO.findByChatRoomId(chatRoomId);
    }
}