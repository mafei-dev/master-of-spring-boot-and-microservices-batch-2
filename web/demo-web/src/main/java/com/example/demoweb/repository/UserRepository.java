package com.example.demoweb.repository;

import com.example.demoweb.entity.UserEntity;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    public void saveUser(Session session, UserEntity user) {
        session.save(user);
    }
}
