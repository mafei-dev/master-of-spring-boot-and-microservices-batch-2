package com.example.demoweb.service;

import com.example.demoweb.dto.NewUserDetailDTO;
import com.example.demoweb.entity.UserContactEntity;
import com.example.demoweb.entity.UserEntity;
import com.example.demoweb.exception.EmailNotFoundException;
import com.example.demoweb.lib.NotificationService;
import com.example.demoweb.lib.impl.NotificationServiceImpl;
import com.example.demoweb.repository.UserContactRepository;
import com.example.demoweb.repository.UserRepository;
import com.example.demoweb.util.DatabaseConfig;
import com.example.demoweb.util.ImageProcesses;
import com.example.demoweb.util.MailSender;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserContactRepository userContactRepository;

    @Autowired
    private MailSender mailSender;
    @Autowired
    private ImageProcesses imageProcesses;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("hikariDataSource")
    private DataSource hikaruDataSource;

    public void addUserWithJdbcDatasource(NewUserDetailDTO userDetail) {
        this.saveNewUser(dataSource, userDetail);
    }

    public void addUserWithHikariDatasource(NewUserDetailDTO userDetail) {
        this.saveNewUser(hikaruDataSource, userDetail);
    }

    public void saveNewUserDefault(NewUserDetailDTO userDetail) {
        this.saveNewUser(dataSource, userDetail);
    }


    private void saveNewUser(DataSource dataSource, NewUserDetailDTO userDetail) {
        System.out.println("notificationService = " + notificationService);
        System.out.println("dataSource = " + dataSource);

        /*if (userDetail.getContactList().size() < 3) {
            throw new RuntimeException("the contact list is less than 3");
        }*/
        //business logic
        //01-save the new user [user-table: UserRepository].
        String userId = UUID.randomUUID().toString();

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            connection.setReadOnly(false);
            this.userRepository.saveUser(
                    connection,
                    UserEntity.builder()
                            .pk(userId)
                            .username(userDetail.getUsername())
                            .age(userDetail.getAge())
                            .build()
            );
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
