package com.example.orderservice.service.test;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TestService {


    @CircuitBreaker(name = "cb1", fallbackMethod = "helloFallback")
    //helloStringInt [method signature]
    //StringInt [signature]
    public String hello(String message,int age) {
        System.out.println("hello:message = " + message + ", age = " + age);
        System.out.println("TestService.hello");
        if (false) {
            return LocalDateTime.now().toString();
        }
        try {
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("test");
    }

    //StringIntException [signature should be in here]
    public String helloFallback(String message,int age,Exception exception) {
        System.out.println("helloFallback:message = " + message + ", age = " + age);
        System.out.println("Exception : " + exception.getClass().getSimpleName());
        return LocalDateTime.now().toString();
    }

}
