package com.example.demoweb.repository;

import com.example.demoweb.db.Database;
import com.example.demoweb.entity.UserContactEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserContactRepository {
    public void saveUserContacts(List<UserContactEntity> userContactEntityList, String userId) {
        //access the database to save a new user contact
        Database.userContactTable.put(
                userId,
                userContactEntityList
        );
        System.out.println("save " + userContactEntityList.size() + " contact(s).");

    }

}
