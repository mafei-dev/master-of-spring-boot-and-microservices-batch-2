package com.example.orderservice.dto;

import lombok.Builder;
import lombok.Data;

public class CreateOrderDTO {

    @Data
    @Builder
    public static class Request {
        private String username;
        private Double amount;
    }

}
