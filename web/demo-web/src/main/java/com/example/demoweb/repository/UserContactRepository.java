package com.example.demoweb.repository;

import com.example.demoweb.entity.UserContactEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserContactRepository {
    public void saveUserContacts(Session session, List<UserContactEntity> userContactEntityList) {
        userContactEntityList.forEach(session::save);
    }

}
