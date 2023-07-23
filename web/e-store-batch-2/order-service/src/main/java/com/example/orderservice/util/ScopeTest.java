package com.example.orderservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class ScopeTest {

    @Value("${my.property}")
    private String value;

    public ScopeTest() {
        System.out.println("ScopeTest.ScopeTest");
    }

    public String getValue() {
        return this.value;
    }

}
