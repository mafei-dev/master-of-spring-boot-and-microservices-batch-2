package com.example.demoweb.controller.rest;

import com.example.demoweb.db.DB;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping(path = "/user")
//REST
public class UserController {


    @GetMapping(path = "/{username}")
    public ResponseEntity<?> getUser(@PathVariable("username") String username) {
        //process
        try {
            return ResponseEntity.ok(DB.getUser(username));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(
                            Collections.singletonMap("msg", e.getMessage())
                    );
        }
    }


    @PostMapping
    public void addUser(@RequestBody DB.User user) {
        System.out.println("user = " + user);
        //process
        DB.addNewUser(user);
    }


    @PutMapping
    public void updateUser(@RequestBody DB.User oldUser) {
        //process
        DB.updateUser(oldUser);
    }

    @DeleteMapping
    public void deleteUser(
            @RequestParam("username") String username,
            @RequestParam(value = "isActive") Boolean isActive
    ) {
        //process
        System.out.println("username = " + username + ", isActive = " + isActive);
        DB.deleteUser(username);
    }
}
