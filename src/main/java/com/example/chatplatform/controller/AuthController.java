package com.example.chatplatform.controller;

import com.example.chatplatform.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");
        authService.register(username, password);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");
        boolean success = authService.authenticate(username, password);

        if (success) {
            // Set authentication in the SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    @GetMapping("/status")
    public ResponseEntity<Map<String, Boolean>> status() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
        Map<String, Boolean> response = Map.of("isAuthenticated", isAuthenticated);
        return ResponseEntity.ok(response);
    }
}