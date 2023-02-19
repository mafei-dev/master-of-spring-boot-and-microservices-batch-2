package org.example;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EStoreMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext("org.example");
        UserService bean = context1.getBean("mongo", UserService.class);
        System.out.println("bean1 = " + bean);
        bean.save(null);
    }
}