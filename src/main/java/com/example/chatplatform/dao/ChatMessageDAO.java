package com.example.chatplatform.dao;

import com.example.chatplatform.model.ChatMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ChatMessageDAO {

    private final JdbcTemplate jdbcTemplate;

    public ChatMessageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for ChatMessage
    private static class ChatMessageRowMapper implements RowMapper<ChatMessage> {
        @Override
        public ChatMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
            ChatMessage message = new ChatMessage();
            message.setId(rs.getLong("id"));
            message.setSender(rs.getString("sender"));
            message.setContent(rs.getString("content"));
            message.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            message.setChatRoomId(rs.getLong("chat_room_id"));
            return message;
        }
    }

    // Insert a new chat message
    public int save(ChatMessage message) {
        String sql = "INSERT INTO chat_message (sender, content, timestamp, chat_room_id) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, message.getSender(), message.getContent(),
                message.getTimestamp(), message.getChatRoomId());
    }

    // Retrieve messages by chat room ID
    public List<ChatMessage> findByChatRoomId(Long chatRoomId) {
        String sql = "SELECT * FROM chat_message WHERE chat_room_id = ?";
        return jdbcTemplate.query(sql, new ChatMessageRowMapper(), chatRoomId);
    }
}