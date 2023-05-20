package com.example.userservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

public class CreatedUserViewModel {
    @Data
    @Builder
    public static class Request {
        private String username;
        private String tel;
        @JsonProperty("is_active")
        private boolean isActive;
    }

    @Data
    @Builder
    public static class Response {
        @JsonProperty("user_id")
        private String userId;
        private String username;
        private String tel;
        @JsonProperty("is_active")
        private boolean isActive;
    }

}
