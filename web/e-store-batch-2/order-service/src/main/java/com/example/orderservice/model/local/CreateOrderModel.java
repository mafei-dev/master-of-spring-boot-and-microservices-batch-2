package com.example.orderservice.model.local;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

public class CreateOrderModel {

    @Data
    @Builder
    public static class Request {
        private String username;
        private Double amount;
    }

    @Data
    @Builder
    public static class Response {
        @JsonProperty("order_id")
        private String orderId;
    }


}
