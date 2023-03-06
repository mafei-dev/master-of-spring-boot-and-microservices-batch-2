package com.example.demoweb.db;

import com.example.demoweb.entity.UserContactEntity;
import com.example.demoweb.entity.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    //user_table
    public static final List<UserEntity> userTable = new ArrayList<>();
    //user_contact_table
    public static final Map<String/*user_id*/, List<UserContactEntity>> userContactTable = new HashMap<>();

}
