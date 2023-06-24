package com.example.orderservice.dto;

import com.example.orderservice.model.local.OrderStatusModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderStatusDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {

        private boolean userServiceIsDone = true;
        private String username;
        private String userId;
        private String tel;
        //        private String deliveryAddress;
        private String orderId;
        private String orderDate;
        private Double orderAmount;

        private boolean paymentServiceIsDone = true;
        private String transactionId;
        private String transactionStatus;

        private List<OrderStatusModel.Response.OrderStatus> statuses;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class OrderStatus {
            private String statusDatetime;
            private String status;
        }
    }
}
