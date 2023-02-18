package org.example;

import org.example.annotation.Bean;
import org.example.entity.UserEntity;

@Bean
public class UserServiceMysqlImpl implements UserService {
    @Override
    public void save(UserEntity user) {
        System.out.println("UserServiceMysqlImpl.save");
    }
}
