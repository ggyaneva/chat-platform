package com.example.chatplatform.dao;

import com.example.chatplatform.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/chat_db";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "gabi2004";

    /**
     * Find a user by their username.
     */
    public User findByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }

    /**
     * Save a new user to the database.
     */
    public boolean saveUser(User user) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.executeUpdate();
            return true; // User saved successfully
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Failed to save user
    }
}