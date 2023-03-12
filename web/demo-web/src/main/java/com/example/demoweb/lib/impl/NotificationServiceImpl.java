package com.example.demoweb.lib.impl;

import com.example.demoweb.lib.NotificationService;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public void send(Object data) {
        System.out.println("NotificationServiceImpl.send");
    }
}
