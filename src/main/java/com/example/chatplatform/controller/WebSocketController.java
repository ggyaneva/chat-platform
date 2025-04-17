package com.example.chatplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class WebSocketController extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(@Nullable WebSocketSession session) {
        if (session != null) {
            sessions.add(session);
            logger.info("New WebSocket connection established: {}", session.getId());
            sendSystemMessage(session, "Welcome! Your session ID is: " + session.getId());
        } else {
            logger.warn("Attempted to establish a null WebSocket session.");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
            if (validateMessage(chatMessage)) {
                broadcastMessage(chatMessage);
            } else {
                logger.warn("Invalid message format received from session {}: {}", session.getId(), message.getPayload());
                sendErrorMessage(session, "Invalid message format.");
            }
        } catch (IOException e) {
            logger.error("Error parsing message from session {}: {}", session.getId(), e.getMessage());
            sendErrorMessage(session, "Failed to process the message.");
        }
    }

    @Override
    public void afterConnectionClosed(@Nullable WebSocketSession session, @Nullable org.springframework.web.socket.CloseStatus status) {
        if (session != null) {
            sessions.remove(session);
            logger.info("WebSocket connection closed: {}", session.getId());
        } else {
            logger.warn("Attempted to close a null WebSocket session.");
        }
    }

    private void broadcastMessage(ChatMessage chatMessage) {
        try {
            String messageJson = objectMapper.writeValueAsString(chatMessage);

            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(messageJson));
                }
            }
        } catch (IOException e) {
            logger.error("Error broadcasting message: {}", e.getMessage());
        }
    }

    private void sendSystemMessage(WebSocketSession session, String message) {
        try {
            ChatMessage systemMessage = new ChatMessage("SYSTEM", message);
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(systemMessage)));
        } catch (IOException e) {
            logger.error("Error sending system message to session {}: {}", session.getId(), e.getMessage());
        }
    }

    private void sendErrorMessage(WebSocketSession session, String errorMessage) {
        try {
            ChatMessage error = new ChatMessage("ERROR", errorMessage);
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(error)));
        } catch (IOException e) {
            logger.error("Error sending error message to session {}: {}", session.getId(), e.getMessage());
        }
    }

    private boolean validateMessage(ChatMessage message) {
        return message.getSender() != null && !message.getSender().isEmpty()
                && message.getContent() != null && !message.getContent().isEmpty();
    }

    @Setter
    @Getter
    private static class ChatMessage {
        private String sender;
        private String content;

        public ChatMessage() {}

        public ChatMessage(String sender, String content) {
            this.sender = sender;
            this.content = content;
        }
    }
}