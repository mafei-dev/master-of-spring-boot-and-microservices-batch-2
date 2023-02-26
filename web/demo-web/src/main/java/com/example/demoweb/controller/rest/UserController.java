package com.example.demoweb.controller.rest;

import com.example.demoweb.db.DB;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    //user/{username}
    @RequestMapping(path = "/{username}", method = {RequestMethod.GET})
    @ResponseBody//application/json; charset=utf-8 | REST
    public DB.User getUser(@PathVariable("username") String username) {
        //process
        return DB.getUser(username);
    }


    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody//application/json; charset=utf-8 | REST
    public void addUser(@RequestBody DB.User user) {
        System.out.println("user = " + user);
        //process
        DB.addNewUser(user);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    @ResponseBody//application/json; charset=utf-8 | REST
    public void updateUser(@RequestBody DB.User oldUser) {
        //process
        DB.updateUser(oldUser);
    }


    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    @ResponseBody//application/json; charset=utf-8 | REST
    public void deleteUser(
            @RequestParam("username") String username,
            @RequestParam(value = "isActive") Boolean isActive
    ) {
        //process
        System.out.println("username = " + username + ", isActive = " + isActive);
        DB.deleteUser(username);
    }


}
