package org.example;


import org.example.annotation.Autowired;
import org.example.core.SpringContext;
import org.example.entity.UserEntity;

public class EStoreMain {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            UserService userService = SpringContext.getBean(UserServiceMongoImpl.class);
            System.out.println("userService = " + userService);
            userService.save(new UserEntity());
            System.out.println("Hello world!");
        }
    }
}