package com.example.chatplatform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class WebSocketController extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON parsing

    @Override
    public void afterConnectionEstablished(@Nullable WebSocketSession session) {
        sessions.add(session);
        assert session != null;
        System.out.println("New WebSocket connection established: " + session.getId());
        sendSystemMessage(session, "Welcome! Your session ID is: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            System.out.println("Received message from session " + session.getId() + ": " + message.getPayload());

            // Parse the message as JSON
            ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

            // Broadcast the parsed message
            broadcastMessage(chatMessage);

        } catch (IOException e) {
            System.err.println("Error handling message: " + e.getMessage());
            sendErrorMessage(session);
        }
    }

    @Override
    public void afterConnectionClosed(@Nullable WebSocketSession session, @Nullable org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
        assert session != null;
        System.out.println("WebSocket connection closed: " + session.getId());
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
            System.err.println("Error broadcasting message: " + e.getMessage());
        }
    }

    private void sendSystemMessage(WebSocketSession session, String message) {
        try {
            ChatMessage systemMessage = new ChatMessage("SYSTEM", message);
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(systemMessage)));
        } catch (IOException e) {
            System.err.println("Error sending system message: " + e.getMessage());
        }
    }

    private void sendErrorMessage(WebSocketSession session) {
        try {
            ChatMessage error = new ChatMessage("ERROR", "Invalid message format. Please send valid JSON.");
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(error)));
        } catch (IOException e) {
            System.err.println("Error sending error message: " + e.getMessage());
        }
    }

    // Inner class to represent chat messages
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