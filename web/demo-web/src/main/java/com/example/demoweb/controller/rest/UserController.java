package com.example.demoweb.controller.rest;

import com.example.demoweb.dto.NewUserDetailDTO;
import com.example.demoweb.exception.EmailNotFoundException;
import com.example.demoweb.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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


    @GetMapping
    public Object getUsers(@RequestParam("usernamePrefix") String usernamePrefix) {
        return this.userService.getUsers(usernamePrefix);
    }

    @GetMapping("/byq")
    public Object getUserByUsernameQuery(@RequestParam("username") String username) {
        return this.userService.getUserByUsernameQuery(username);
    }


    @DeleteMapping
    public void delete(@RequestParam("userId") String userId) {
        this.userService.delete(userId);
    }

    @PutMapping
    public void updateUser(@RequestBody Map<String, Object> data) {
        this.userService.updateUser(
                Integer.parseInt(data.get("newAge").toString()),
                data.get("username").toString()
        );
    }


}
