package org.example;


import lombok.AllArgsConstructor;
import org.example.dao.UserDaoMongoImpl;
import org.example.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component(value = "mongo")
//@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
//@AllArgsConstructor
public class UserServiceMongoImpl implements UserService {

    @Autowired
    @Qualifier("mysqlDao")
    private UserDao userDao;


    @PostConstruct
    public void postConstruct() {
        System.out.println("UserServiceMongoImpl.postConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("UserServiceMongoImpl.preDestroy");
    }


    @Override
    public void save(UserEntity user) {
        System.out.println("UserServiceMongoImpl.save");
        userDao.save(null);
    }

}
