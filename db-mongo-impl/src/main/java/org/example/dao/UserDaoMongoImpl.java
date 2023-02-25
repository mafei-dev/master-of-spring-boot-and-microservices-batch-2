package org.example.dao;


import org.example.UserDao;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("mongoDao")
@Lazy
public class UserDaoMongoImpl implements UserDao {


    public UserDaoMongoImpl() {
        System.out.println("UserDaoMongoImpl.UserDaoMongoImpl");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("UserDaoMongoImpl.postConstruct");
    }


    @Override
    public void save(String s) {
        System.out.println("UserDaoMongoImpl.save");
    }
}
