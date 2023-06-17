package com.example.orderservice.model.local;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CreateOrderModel {

    @Data
    @Builder
    public static class Request {
        @NotNull(message = "username can not be null!")
        @NotBlank(message = "username can not be null!")
        @Email(message = "username should be an email!")
        private String username;

        @NotNull(message = "amount can not be null!")
        @Positive(message = "amount can not be less than zero!")
        private Double amount;
    }

    @Data
    @Builder
    public static class Response {
        @JsonProperty("order_id")
        private String orderId;
    }


}
