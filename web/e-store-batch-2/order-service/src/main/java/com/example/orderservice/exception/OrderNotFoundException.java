package com.example.orderservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class OrderNotFoundException extends Exception {
    private final String orderId;

    public OrderNotFoundException(String orderId) {
        this.orderId = orderId;
    }

    public OrderNotFoundException(String message, String orderId) {
        super(message);
        this.orderId = orderId;
    }
}
