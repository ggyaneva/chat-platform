package com.example.chatplatform.service;

import com.example.chatplatform.dao.UserDAO;
import com.example.chatplatform.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User authenticate(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return null;
        }

        // Validate hashed password
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean registerUser(String username, String password, String role) {
        if (userDAO.findByUsername(username) != null) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Hash password
        user.setRole(role);
        return userDAO.saveUser(user);
    }

    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}