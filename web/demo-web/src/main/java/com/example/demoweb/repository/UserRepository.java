package com.example.demoweb.repository;

import com.example.demoweb.db.Database;
import com.example.demoweb.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public void saveUser(UserEntity user) {
        //access the database to save a new user
        Database.userTable.add(user);
        System.out.println("user saved");
    }
}
