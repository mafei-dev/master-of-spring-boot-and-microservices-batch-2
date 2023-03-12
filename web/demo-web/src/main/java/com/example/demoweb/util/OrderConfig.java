package com.example.demoweb.util;

import com.example.demoweb.lib.NotificationService;
import com.example.demoweb.lib.impl.NotificationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfig {

    @Bean
    public NotificationService notificationService() {
        NotificationServiceImpl notificationService = new NotificationServiceImpl();
        System.out.println("notificationService = " + notificationService);
        return notificationService;
    }
}
