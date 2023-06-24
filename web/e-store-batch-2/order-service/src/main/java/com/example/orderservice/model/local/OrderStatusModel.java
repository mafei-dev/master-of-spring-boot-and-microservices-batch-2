package com.example.orderservice.model.local;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderStatusModel {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {


        private List<OrderStatus> statuses;
        @JsonProperty("user_detail")
        private UserDetail userDetail;
        @JsonProperty("order_detail")
        private OrderDetail orderDetail;
        @JsonProperty("transaction_detail")
        private TransactionData transactionData;


        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class OrderStatus {
            @JsonProperty("status_datetime")
            private String statusDatetime;
            private String status;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class UserDetail {
            private String username;
            @JsonProperty("user_id")
            private String userId;
            private String tel;
            @JsonProperty("delivery_address")
            private String deliveryAddress;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class OrderDetail {
            @JsonProperty("order_id")
            private String orderId;
            @JsonProperty("order_date")
            private String orderDate;
            @JsonProperty("order_amount")
            private Double orderAmount;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class TransactionData {
            @JsonProperty("transaction_id")
            private String transactionId;
            @JsonProperty("transaction_status")
            private String transactionStatus;
        }


    }
}
