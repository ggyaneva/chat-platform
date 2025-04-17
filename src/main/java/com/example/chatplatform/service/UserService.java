package com.example.chatplatform.service;

import com.example.chatplatform.dao.UserDAO;
import com.example.chatplatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User authenticate(String username, String password) {
        // Fetch user by username
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return null; // User not found
        }

        // Validate password (plain-text comparison for simplicity; hash in production)
        if (user.getPassword().equals(password)) {
            return user; // Authentication successful
        }

        return null; // Invalid password
    }

    public boolean registerUser(String username, String password, String role) {
        // Check if the username already exists
        if (userDAO.findByUsername(username) != null) {
            return false; // Username already taken
        }

        // Save the new user
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // Use password hashing in production
        user.setRole(role);
        return userDAO.saveUser(user);
    }

    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}