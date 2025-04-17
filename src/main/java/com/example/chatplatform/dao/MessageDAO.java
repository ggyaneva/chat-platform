package com.example.chatplatform.dao;

import com.example.chatplatform.model.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MessageDAO {

    private final JdbcTemplate jdbcTemplate;

    public MessageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Message> getMessagesByChatRoomId(Long chatRoomId) {
        String sql = "SELECT * FROM messages WHERE chat_room_id = ?";
        return jdbcTemplate.query(sql, new MessageRowMapper(), chatRoomId);
    }

    public void addMessage(Long chatRoomId, String content, Long senderId) {
        String sql = "INSERT INTO messages (chat_room_id, content, sender_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, chatRoomId, content, senderId);
    }

    private static class MessageRowMapper implements RowMapper<Message> {
        @Override
        public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
            Message message = new Message();
            message.setId(rs.getLong("id"));
            message.setContent(rs.getString("content"));
            return message;
        }
    }
}