package com.example.apigateway.predicate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.function.Predicate;

@Component
public class Counter extends AbstractRoutePredicateFactory<Counter.Config> {

    public Counter() {
        super(Counter.Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        //check the condition
        System.out.println("Counter.apply:1");
        return serverWebExchange -> {
            System.out.println("Counter.apply:2");
            String count = serverWebExchange.getRequest().getHeaders()
                    .getOrDefault("X-Count", new ArrayList<>())
                    .get(0);
            //cont from the request
            int countByInt = Integer.parseInt(count);
            //value from the config for checking the condition
            Integer maxValue = config.getMaxValue();

            if (countByInt <= maxValue) {
                return true;
            } else {
                return false;
            }
        };
    }

    @Validated
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Config {
        @NotNull
        private Integer maxValue;
    }
}
