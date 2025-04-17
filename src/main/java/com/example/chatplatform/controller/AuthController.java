package com.example.chatplatform.controller;

import com.example.chatplatform.model.User;
import com.example.chatplatform.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Login a user by validating their credentials.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session) {
        // Authenticate user
        User user = userService.authenticate(username, password);
        if (user == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // Store user details in session
        session.setAttribute("role", user.getRole());
        session.setAttribute("username", user.getUsername());

        // Respond with role and success message
        return ResponseEntity.ok(Map.of(
                "role", user.getRole(),
                "message", "Logged in successfully"
        ));
    }

    /**
     * Logout the current user by invalidating the session.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}