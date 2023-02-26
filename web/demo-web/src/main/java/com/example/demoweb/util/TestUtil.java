package com.example.demoweb.util;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Lazy
public class TestUtil {

    public TestUtil() {
        System.out.println("TestUtil.TestUtil");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("TestUtil.postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("TestUtil.preDestroy");
    }

    public void test() {
        System.out.println("TestUtil.test");
    }

}
