package com.example.demoweb.service;

import com.example.demoweb.dto.NewUserDetailDTO;
import com.example.demoweb.entity.UserContactEntity;
import com.example.demoweb.entity.UserEntity;
import com.example.demoweb.exception.EmailNotFoundException;
import com.example.demoweb.lib.NotificationService;
import com.example.demoweb.repository.UserContactRepository;
import com.example.demoweb.repository.UserRepository;
import com.example.demoweb.util.ImageProcesses;
import com.example.demoweb.util.MailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final UserContactRepository userContactRepository;

    private final MailSender mailSender;
    private final ImageProcesses imageProcesses;
    private final NotificationService notificationService;


    @Transactional(rollbackFor = {EmailNotFoundException.class})
    public void saveNewUser(NewUserDetailDTO userDetail) throws EmailNotFoundException {
        log.info("thread:{}", Thread.currentThread().getName());

        System.out.println("notificationService = " + notificationService);
        //business logic
        //01-save the new user [user-table: UserRepository].
        String userId = UUID.randomUUID().toString();


        this.userRepository.saveUser(
                UserEntity.builder()
                        .userId(userId)
                        .username(userDetail.getUsername())
                        .userAge(userDetail.getAge())
                        .build()
        );

        List<UserContactEntity> userContactEntityList = new ArrayList<>();
        userDetail.getContactList().forEach(userContact -> {
            UserContactEntity entity = UserContactEntity.builder()
                    .userContactId(UUID.randomUUID().toString())
                    .contactKey(userContact.getKey())
                    .contactValue(userContact.getValue())
                    .userId(userId)
                    .build();
            userContactEntityList.add(entity);
        });

        //02-save new user contacts [user-contact-table: UserContactRepository].
        this.userContactRepository.saveUserContacts(
                userContactEntityList
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
    }
}
