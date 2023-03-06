package com.example.demoweb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class NewUserDetailDTO {
    private String username;
    private int age;

    private String userImg;

    private List<UserContact> contactList;

    @Getter
    @Setter
    @Builder
    public static class UserContact {
        private String key;
        private String value;
    }
}
