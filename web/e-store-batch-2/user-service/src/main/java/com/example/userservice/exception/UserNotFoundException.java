package com.example.userservice.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserNotFoundException extends Exception {
    private final String username;

    public UserNotFoundException(String username) {
        this.username = username;
    }

    public UserNotFoundException(String message, String username) {
        super(message);
        this.username = username;
    }
}
