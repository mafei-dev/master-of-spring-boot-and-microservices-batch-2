package com.example.userservice.util;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    @Getter
    private final String msg;
    private final Map<String, Object> data = new HashMap<>();

    public ErrorResponse(String msg) {
        this.msg = msg;
    }

    public void put(String key, Object value) {
        this.data.put(key, value);
    }

    public Object get(String key) {
        return this.data.get(key);
    }
}
