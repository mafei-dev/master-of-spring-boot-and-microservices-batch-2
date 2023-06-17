package com.example.orderservice.controller;

import com.example.orderservice.service.test.TestService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final TestService testService;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("--------------------------------");
        CircuitBreaker cb1 = this.circuitBreakerRegistry.circuitBreaker("cb1");
        CircuitBreaker.State state = cb1.getState();
        System.out.println("Before Status : " + state);
        return this.testService.hello("mafei", new Random().nextInt());
    }
}
