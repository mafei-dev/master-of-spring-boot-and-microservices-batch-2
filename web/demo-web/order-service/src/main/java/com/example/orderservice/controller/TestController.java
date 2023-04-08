package com.example.orderservice.controller;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class TestController {

    @GetMapping("/test")
    public Object getDate(HttpServletRequest request) {

        return Data.builder()
                .port(request.getServerPort())
                .time(LocalDateTime.now())
                .build();
    }


    @PostMapping("/place")
    public Object placeOrder(HttpServletRequest request) {
        //do the process with service
        return PlceOrderResponse.builder()
                .port(request.getServerPort())
                .time(LocalDateTime.now())
                .message("Your order has been placed successfully. "+request.getHeader("X-Gateway-In"))
                .orderId(UUID.randomUUID().toString())
                .build();
    }


    @Getter
    @Setter
    @Builder
    public static class Data {
        private Object port;
        private LocalDateTime time;
    }

    @Getter
    @Setter
    @Builder
    public static class PlceOrderResponse {
        private Object port;
        private String message;
        private String orderId;
        private LocalDateTime time;
    }
}
