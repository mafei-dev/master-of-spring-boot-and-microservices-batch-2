package com.example.demoweb.controller.rest;

import com.example.demoweb.db.DB;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
//REST
public class UserController {

    //user/{username}
//    @RequestMapping(path = "/{username}", method = {RequestMethod.GET})
    @GetMapping(path = "/{username}")
    public DB.User getUser(@PathVariable("username") String username) {
        //process
        return DB.getUser(username);
    }


    //    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public void addUser(@RequestBody DB.User user) {
        System.out.println("user = " + user);
        //process
        DB.addNewUser(user);
    }


//    @RequestMapping(method = RequestMethod.PUT)
    @PutMapping
    public void updateUser(@RequestBody DB.User oldUser) {
        //process
        DB.updateUser(oldUser);
    }


//    @RequestMapping(method = RequestMethod.DELETE)
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
