package com.example.paymentservice.controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;

@RestController
//@RequestMapping("/")
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


}
