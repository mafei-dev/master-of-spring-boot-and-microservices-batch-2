package com.example.demoweb.repository;

import com.example.demoweb.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Slf4j
public class UserRepository {

    private final SessionFactory sessionFactory;

    public void saveUser(UserEntity user) {
        Session currentSession = sessionFactory.getCurrentSession();
        log.info("currentSession:{},thread:{}", currentSession, Thread.currentThread().getName());
        currentSession.save(user);
    }
}
