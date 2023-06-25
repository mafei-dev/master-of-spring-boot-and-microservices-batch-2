package com.example.orderservice;

import com.example.orderservice.util.CustomLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class OrderServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(OrderServiceApplication.class, args);

    }

}
