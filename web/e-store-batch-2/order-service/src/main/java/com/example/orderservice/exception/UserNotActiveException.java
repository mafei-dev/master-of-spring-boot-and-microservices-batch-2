package com.example.orderservice.exception;

import lombok.Getter;

@Getter
public class UserNotActiveException extends Exception {

    private final String username;

    public UserNotActiveException(String username) {
        this.username = username;
    }

    public UserNotActiveException(String message, String username) {
        super(message);
        this.username = username;
    }
}
