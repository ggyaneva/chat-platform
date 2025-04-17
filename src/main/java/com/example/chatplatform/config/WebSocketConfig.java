package com.example.chatplatform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import com.example.chatplatform.controller.WebSocketController;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final WebSocketController webSocketController;

    @Value("${application.websocket.path}") // WebSocket endpoint path from properties
    private String webSocketPath;

    public WebSocketConfig(WebSocketController webSocketController) {
        this.webSocketController = webSocketController;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Use specific origins for better security
        registry.addHandler(webSocketController, webSocketPath).setAllowedOrigins("http://localhost:3000");
    }
}