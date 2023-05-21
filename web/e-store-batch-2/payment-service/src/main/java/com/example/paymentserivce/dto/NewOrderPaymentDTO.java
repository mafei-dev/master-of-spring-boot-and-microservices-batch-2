package com.example.paymentserivce.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class NewOrderPaymentDTO {
    @Data
    @Builder
    public static class Request {

        private Double amount;
        private String status;
        private String username;
        private String orderId;
        private String userId;
    }

    @Data
    @Builder
    public static class Response {
        private String orderPaymentId;
        private Double amount;
        private String status;
        private LocalDateTime dateTime;
        private String username;
        private String orderId;
        private String userId;
    }


}
