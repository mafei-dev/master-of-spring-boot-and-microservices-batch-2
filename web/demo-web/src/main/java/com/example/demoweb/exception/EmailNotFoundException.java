package com.example.demoweb.exception;

import lombok.Getter;

@Getter
public class EmailNotFoundException extends RuntimeException {
    private final String username;

    public EmailNotFoundException(String message, String username) {
        super(message);
        this.username = username;
    }
}
