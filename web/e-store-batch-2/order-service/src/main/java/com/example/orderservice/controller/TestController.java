package com.example.orderservice.controller;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.service.access.UserServiceClientAccess;
import com.example.orderservice.service.external.UserServiceClientCloud;
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
    private final UserServiceClientCloud userServiceClientCloud;

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

    @GetMapping("/user-cloud")
    public Object testUserCloud(@RequestParam("username") String username) throws ServiceException {
        System.out.println("--------------------------------");
/*
        this.circuitBreakerRegistry.getAllCircuitBreakers().forEach(circuitBreaker -> {
            System.out.println(circuitBreaker.getName());
        });
*/
        CircuitBreaker cb1 = this.circuitBreakerRegistry.circuitBreaker("UserServiceClientCloudgetUserByNameString");
        CircuitBreaker.State state = cb1.getState();
        System.out.println("Before Status : " + state);
        return userServiceClientCloud.getUserByName(username);
    }


    @GetMapping("/bulkhead")
    public Object testBulkhead(@RequestParam("count") String count) throws ServiceException {

        return userServiceClient.getUserDetails(count);
    }

    @GetMapping("/bulkhead/tp")
    public Object testBulkheadTP(@RequestParam("count") String count) {
        System.out.println("TestController:Thread = " + Thread.currentThread().getName() + ">" + count);
        return userServiceClient.getUserDetailsTP(count);
    }


    //check the main-pool
    @GetMapping("/test-pool-1")
    public void testPool1() throws InterruptedException {
        this.userServiceClient.testPool1();
    }

    @GetMapping("/test-pool-2")
    public void testPool2() {
        this.userServiceClient.testPool2();
    }

}
