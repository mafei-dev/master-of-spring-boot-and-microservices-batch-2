package com.example.orderservice.model.external;

import lombok.Builder;
import lombok.Data;

public class UserViewModal {

    @Data
    @Builder
    public static class UserViewResponse {
        private String userId;
        private String username;
        private String tel;
        private boolean isActive;
    }
}
