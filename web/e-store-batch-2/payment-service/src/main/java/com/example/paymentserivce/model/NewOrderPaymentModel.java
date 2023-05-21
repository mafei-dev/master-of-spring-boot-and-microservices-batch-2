package com.example.paymentserivce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class NewOrderPaymentModel {
    @Data
    @Builder
    public static class Request {

        private Double amount;
        private String status;
        private String username;
        @JsonProperty("order_id")
        private String orderId;
        @JsonProperty("user_id")
        private String userId;
    }

    @Data
    @Builder
    public static class Response {
        @JsonProperty("order_payment_id")
        private String orderPaymentId;
        private Double amount;
        private String status;
        @JsonProperty("datetime")
        private LocalDateTime dateTime;
        private String username;
        @JsonProperty("order_id")
        private String orderId;
        @JsonProperty("user_id")
        private String userId;
    }


}
