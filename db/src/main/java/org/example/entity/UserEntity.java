package org.example.entity;

public class UserEntity {
    private String userId;
    private String username;

    public UserEntity(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public UserEntity() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
