package com.example.demoweb.entity;


import lombok.Builder;

/**
 * user_table
 */
@Builder
public class UserEntity {

    private String pk;
    private String username;
    private int age;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
