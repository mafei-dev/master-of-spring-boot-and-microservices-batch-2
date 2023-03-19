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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

        this.userRepository.findByUsernameContaining(userDetail.getUsername());
      /*  if (user.isPresent()) {
            throw new RuntimeException("the user already exist.");
        }*/
        this.userRepository.save(
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
        this.userContactRepository.saveAll(userContactEntityList);


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

    public List<Map<String, Object>> getUsers(String usernamePrefix) {
        return this.userRepository.findByUsernameContaining(usernamePrefix)
                .stream()
                .map(user -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("username", user.getUsername());
                    data.put("userId", user.getUserId());
                    data.put("age", user.getUserAge());
                    return data;
                })
                .collect(Collectors.toList());
    }


    public void delete(String userId) {
        this.userRepository.deleteById(userId);
    }

    public Object getUserByUsernameQuery(String username) {
        Optional<UserEntity> byUsernameByQuery = this.userRepository.findByUsernameByQuery(username);
        if (byUsernameByQuery.isPresent()) {
            return byUsernameByQuery.get();
        } else {
            throw new RuntimeException("user does not exist.");
        }
    }

    @Transactional
    public void updateUser(int newAge, String username) {
        this.userRepository.updateUser(newAge, username);
    }
}
