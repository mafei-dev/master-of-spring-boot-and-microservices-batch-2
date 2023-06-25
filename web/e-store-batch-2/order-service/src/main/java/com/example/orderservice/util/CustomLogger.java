package com.example.orderservice.util;

import org.springframework.stereotype.Component;

@Component
public class CustomLogger {

    public void log(String message) {
        System.out.println("message = " + message);
    }
}
