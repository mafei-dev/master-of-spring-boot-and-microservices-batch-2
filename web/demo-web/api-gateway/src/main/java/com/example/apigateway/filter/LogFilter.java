package com.example.apigateway.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class LogFilter extends AbstractGatewayFilterFactory<LogFilter.config> {


    public LogFilter() {
        super(LogFilter.config.class);
    }

    @Override
    public GatewayFilter apply(config config) {
        System.out.println("LogFilter.apply:1");
        return (exchange, chain) -> {
            System.out.println("LogFilter.apply:2");
            try {
                ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate();
                //
                requestBuilder.header("X-Message", config.getMessage());
                requestBuilder.header("X-Gateway-In", LocalDateTime.now().toString());
                ServerHttpRequest serverHttpRequest = requestBuilder.build();
                ServerWebExchange exchange1 = exchange.mutate().request(serverHttpRequest).build();
                return chain
                        .filter(exchange1)
                        .then(Mono.fromRunnable(() -> {
                            System.out.println("LogFilter.apply:3");
                            List<String> values = exchange.getResponse()
                                    .getHeaders()
                                    .getOrDefault("X-value", new ArrayList<>());

                            int value = Integer.parseInt(values.get(0));
                            if (value != 1) {
                                exchange.getResponse().setStatusCode(HttpStatus.CONFLICT);
                                exchange.getResponse().setComplete().subscribe();
                            }
                        }));
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        };
    }

    @Validated
    @AllArgsConstructor
    @Getter
    @Setter
    public static class config {
        private String message;
    }
}
