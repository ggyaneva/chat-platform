package com.example.chatplatform.dao;

import com.example.chatplatform.model.ChatRoom;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ChatRoomDAO {

    private final JdbcTemplate jdbcTemplate;

    public ChatRoomDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for ChatRoom
    private static class ChatRoomRowMapper implements RowMapper<ChatRoom> {
        @Override
        public ChatRoom mapRow(ResultSet rs, int rowNum) throws SQLException {
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setId(rs.getLong("id"));
            chatRoom.setName(rs.getString("name"));
            return chatRoom;
        }
    }

    // Insert a new chat room
    public int save(ChatRoom chatRoom) {
        String sql = "INSERT INTO chat_room (name) VALUES (?)";
        return jdbcTemplate.update(sql, chatRoom.getName());
    }

    // Retrieve all chat rooms
    public List<ChatRoom> findAll() {
        String sql = "SELECT * FROM chat_room";
        return jdbcTemplate.query(sql, new ChatRoomRowMapper());
    }
}