package com.example.userservice.dto;

import lombok.Builder;
import lombok.Data;

public class UserViewDTO {

    @Data
    @Builder
    public static class UserViewResponse {
        private String userId;
        private String username;
        private String tel;
        private boolean isActive;
    }
}
