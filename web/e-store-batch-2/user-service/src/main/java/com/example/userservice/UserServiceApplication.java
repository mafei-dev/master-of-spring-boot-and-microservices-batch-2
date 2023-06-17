package com.example.userservice;

import com.example.userservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@AllArgsConstructor
@Slf4j
public class UserServiceApplication implements CommandLineRunner {

    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*UserCreateDTO.UserCreateRequest[] userCreateRequests = new UserCreateDTO.UserCreateRequest[2];
        userCreateRequests[0] = UserCreateDTO.UserCreateRequest.builder()
                .username("Mafei")
                .tel("0712955386")
                .isActive(true)
                .build();
        userCreateRequests[1] = UserCreateDTO.UserCreateRequest.builder()
                .username("Dasun")
                .tel("078362544324")
                .isActive(true)
                .build();

        this.userService
                .save(userCreateRequests)
                .forEach(userCreateResponse -> {
                    log.debug(
                            "username : {}, userId : {}",
                            userCreateResponse.getUsername(),
                            userCreateResponse.getUserId()
                    );
                });*/
    }
}
