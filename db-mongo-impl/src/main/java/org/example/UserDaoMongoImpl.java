package org.example;

import org.example.annotation.Bean;

@Bean
public class UserDaoMongoImpl implements UserDao{
    @Override
    public void save(String s) {
        System.out.println("UserDaoMongoImpl.save");
    }
}
