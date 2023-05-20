package com.example.orderservice.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class TestController {

    private final RestTemplate restTemplate;

    @GetMapping("/test")
    public Object getDate(HttpServletRequest request) {

        return Data.builder()
                .port(request.getServerPort())
                .time(LocalDateTime.now())
                .build();
    }


    @PostMapping("/place")
    public Object placeOrder(HttpServletRequest request, HttpServletResponse response) {
        //do the process with service
        response.setHeader("X-value", "1");
        //Make the payment.
        {

            Map<String, Object> data = new HashMap<>();
            ResponseEntity<Object> entity =
                    this.restTemplate.postForEntity("http://payment-service/make-payment", data, Object.class);
            Object body = entity.getBody();
            System.out.println("body : " + body);
        }
        return PlceOrderResponse.builder()
                .port(request.getServerPort())
                .time(LocalDateTime.now())
                .message("Your order has been placed successfully. " + request.getHeader("X-Gateway-In"))
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
