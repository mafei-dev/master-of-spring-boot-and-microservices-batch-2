package org.example;

import org.example.annotation.Autowired;
import org.example.annotation.Bean;
import org.example.entity.UserEntity;

@Bean
public class UserServiceMongoImpl implements UserService {

    @Autowired
    private UserDaoMongoImpl userDao;

    @Override
    public void save(UserEntity user) {
        System.out.println("UserServiceMongoImpl.save");
        System.out.println("userDao = " + userDao);
    }
}
