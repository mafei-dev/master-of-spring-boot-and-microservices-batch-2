package org.example.dao;


import org.example.UserDao;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("mongoDao")
public class UserDaoMongoImpl implements UserDao {


    @PostConstruct
    public void postConstruct() {
        System.out.println("UserDaoMongoImpl.postConstruct");
    }


    @Override
    public void save(String s) {
        System.out.println("UserDaoMongoImpl.save");
    }
}
