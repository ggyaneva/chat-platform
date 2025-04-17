package com.example.chatplatform.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    // Getters and Setters
    private Long id;
    private String username;
    private String password; // Plain password for simplicity; hash in production
    private String role; // e.g., "ADMIN" or "USER"

}