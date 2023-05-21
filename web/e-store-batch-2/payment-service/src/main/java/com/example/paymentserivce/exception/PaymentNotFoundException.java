package com.example.paymentserivce.exception;

import lombok.Getter;

@Getter
public class PaymentNotFoundException extends Exception {

    private final String orderId;

    public PaymentNotFoundException(String orderId) {
        this.orderId = orderId;
    }

    public PaymentNotFoundException(String message, String orderId) {
        super(message);
        this.orderId = orderId;
    }
}
