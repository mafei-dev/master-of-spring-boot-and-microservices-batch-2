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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private SessionFactory sessionFactory;


    public void saveNewUserDefault(NewUserDetailDTO userDetail) {
        this.saveNewUser(userDetail);
    }


    private void saveNewUser(NewUserDetailDTO userDetail) {
        System.out.println("notificationService = " + notificationService);

        /*if (userDetail.getContactList().size() < 3) {
            throw new RuntimeException("the contact list is less than 3");
        }*/
        //business logic
        //01-save the new user [user-table: UserRepository].
        String userId = UUID.randomUUID().toString();
        try (Session currentSession = sessionFactory.openSession()) {

            Transaction transaction = currentSession.beginTransaction();

            this.userRepository.saveUser(
                    currentSession,
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
                    currentSession,
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
            //**commit the data into the database after all are done.
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
