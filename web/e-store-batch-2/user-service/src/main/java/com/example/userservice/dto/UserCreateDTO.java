package com.example.userservice.dto;

import lombok.Builder;
import lombok.Data;

public class UserCreateDTO {

    @Data
    @Builder
    public static class UserCreateRequest {
        private String username;
        private String tel;
        private boolean isActive;
    }

    @Data
    @Builder
    public static class UserCreateResponse {
        private String userId;
        private String username;
        private String tel;
        private boolean isActive;
    }
}
