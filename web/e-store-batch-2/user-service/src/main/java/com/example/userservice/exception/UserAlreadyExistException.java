package com.example.userservice.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistException extends Exception {
    private final String username;

    public UserAlreadyExistException(String username) {
        this.username = username;
    }

    public UserAlreadyExistException(String message, String username) {
        super(message);
        this.username = username;
    }
}
