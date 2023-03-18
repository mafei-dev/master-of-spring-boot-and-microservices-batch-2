package com.example.demoweb.repository;

import com.example.demoweb.entity.UserContactEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class UserContactRepository {

    private final SessionFactory sessionFactory;

    public void saveUserContacts(List<UserContactEntity> userContactEntityList) {
        Session currentSession = sessionFactory.getCurrentSession();
        log.info("currentSession:{},thread:{}", currentSession, Thread.currentThread().getName());
        userContactEntityList.forEach(currentSession::save);
    }

}
