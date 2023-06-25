package com.example.orderservice.controller;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.service.access.UserServiceClientAccess;
import com.example.orderservice.service.external.UserServiceClient;
import com.example.orderservice.service.test.TestService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final TestService testService;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final UserServiceClientAccess userServiceClient;

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("--------------------------------");
        CircuitBreaker cb1 = this.circuitBreakerRegistry.circuitBreaker("cb1");
        CircuitBreaker.State state = cb1.getState();
        System.out.println("Before Status : " + state);
        return this.testService.hello("mafei", new Random().nextInt());
    }

    @GetMapping("/user")
    public Object testUser(@RequestParam("username") String username) throws ServiceException {
        System.out.println("--------------------------------");
        CircuitBreaker cb1 = this.circuitBreakerRegistry.circuitBreaker("cb1");
        CircuitBreaker.State state = cb1.getState();
        System.out.println("Before Status : " + state);
        return userServiceClient.getUserByName(username);
    }
}
