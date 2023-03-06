package com.example.demoweb.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserContactEntity {
    private String pk;
    private String key;
    private String value;
    private String userId;
}
