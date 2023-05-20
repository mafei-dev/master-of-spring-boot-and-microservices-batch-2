package com.example.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class GlobalFilters {

    @Bean
    @Order(value = -1)
    //-Integer.Max 0  Integer.Max
    public GlobalFilter customGlobalFilter1() {
        System.out.println("GlobalFilters.customGlobalFilter1");
        return (exchange, chain) -> {
            //pre filter
            System.out.println("GlobalFilters.customGlobalFilter1:pre");
            return chain
                    .filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        System.out.println("GlobalFilters.customGlobalFilter1:post");
                        //post filter
                    }));
        };
    }

    @Bean
    @Order(value = 0)
    public GlobalFilter customGlobalFilter2() {
        System.out.println("GlobalFilters.customGlobalFilter2");
        return (exchange, chain) -> {
            //pre filter
            System.out.println("GlobalFilters.customGlobalFilter2:pre");
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        System.out.println("GlobalFilters.customGlobalFilter2:post");
                        //post filter

                    }));
        };
    }

    @Bean
    @Order(value = 1)
    public GlobalFilter customGlobalFilter3() {
        System.out.println("GlobalFilters.customGlobalFilter3");
        return (exchange, chain) -> {
            System.out.println("GlobalFilters.customGlobalFilter3:pre");
            //pre filter
            return chain
                    .filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        System.out.println("GlobalFilters.customGlobalFilter3:post");
                        //post filter

                    }));
        };
    }


}
