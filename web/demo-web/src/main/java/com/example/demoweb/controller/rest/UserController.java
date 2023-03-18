package com.example.demoweb.controller.rest;

import com.example.demoweb.dto.NewUserDetailDTO;
import com.example.demoweb.exception.EmailNotFoundException;
import com.example.demoweb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(path = "/user")
//REST
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    public void addUser(HttpEntity<NewUserDetailDTO> entity) throws EmailNotFoundException {
        this.userService.saveNewUser(Objects.requireNonNull(entity.getBody()));
    }

}
