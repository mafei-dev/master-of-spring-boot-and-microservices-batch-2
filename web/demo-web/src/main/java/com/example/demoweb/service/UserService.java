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
    private DataSource hikaruDataSource;


    public void saveNewUserDefault(NewUserDetailDTO userDetail) {
        this.saveNewUser(hikaruDataSource, userDetail);
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

            List<UserContactEntity> userContactEntityList = new ArrayList<>();
            userDetail.getContactList().forEach(userContact -> {
                UserContactEntity entity = UserContactEntity.builder()
                        .pk(UUID.randomUUID().toString())
                        .key(userContact.getKey())
                        .value(userContact.getValue())
                        .userId(userId)
                        .build();
                userContactEntityList.add(entity);
            });

            //02-save new user contacts [user-contact-table: UserContactRepository].
            this.userContactRepository.saveUserContacts(
                    connection,
                    userContactEntityList,
                    userId
            );


            //03-save the user's image
            this.imageProcesses.saveImage(userDetail.getUserImg());
            //04-sent an email to the saved user
            Optional<NewUserDetailDTO.UserContact> email = userDetail
                    .getContactList()
                    .stream()
                    .filter(userContact ->
                            userContact.getKey().equalsIgnoreCase("email")
                    )
                    .findFirst();

            if (email.isPresent()) {
                this.mailSender.send(email.get().getValue());
            } else {
                throw new EmailNotFoundException(
                        "your email has not been provided.",
                        userDetail.getUsername()
                );
            }
            //**commit the data into the database after all are done.
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
