package org.example;

import org.example.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component(value = "mysql")
public class UserServiceMysqlImpl implements UserService {
    @Override
    public void save(UserEntity user) {
        System.out.println("UserServiceMysqlImpl.save");
    }
}
