package com.example.paymentservice.controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class TestController {

    @GetMapping
    public Object getDate() {
        return LocalDateTime.now();
    }

    @PostMapping("/make-payment")
    public Object makePayment() {
        System.out.println("TestController.makePayment");
        return Collections.singletonMap("msg", "payment success!");
    }

    @GetMapping("/get-user-payment")
    public Map<String, Double> getUserPayment(@RequestParam("username") String username) {
        System.out.println("TestController.getUserPayment");
        return Collections.singletonMap("amount", 32434.41);
    }
}
