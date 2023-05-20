package com.example.orderservice.service.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "payment-service", path = "/payment")
public interface PaymentClientService {

    @PostMapping("/make-payment")
    Object makePayment();

    @GetMapping("/get-user-payment")
    Map<String, Double> getUserPayment(@RequestParam("username") String username);

}
