package org.example.dao;


import org.example.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mongoDao")
public class UserDaoMongoImpl implements UserDao {


    @Override
    public void save(String s) {
        System.out.println("UserDaoMongoImpl.save");
    }
}
