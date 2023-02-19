package org.example.dao;

import org.example.UserDao;
import org.springframework.stereotype.Component;

@Component(value = "mysqlDao")
public class UserDaoMysqlImpl implements UserDao {
    @Override
    public void save(String s) {
        System.out.println("UserDaoMysqlImpl.save");
    }
}
