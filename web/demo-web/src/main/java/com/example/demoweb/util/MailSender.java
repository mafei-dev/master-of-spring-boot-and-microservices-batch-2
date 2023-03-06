package com.example.demoweb.util;

import com.example.demoweb.anotation.UtilComponent;

@UtilComponent(value = "myBean")
public class MailSender {

    public void send(String mail) {
        System.out.println("The mail has been sent.");
    }
}
