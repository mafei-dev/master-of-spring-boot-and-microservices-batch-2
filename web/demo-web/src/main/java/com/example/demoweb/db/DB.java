package com.example.demoweb.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DB {


    public static class User {
        //{username : mafei, age : 12}
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }

        public User() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    private static final List<User> USERS = new ArrayList<>();

    static {
        USERS.add(new User("mafei", 29));
        USERS.add(new User("gayan", 23));
        USERS.add(new User("chamath", 29));
        USERS.add(new User("dasun", 21));
    }

    public static User getUser(String username) {
        Optional<User> first = USERS.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        if (first.isPresent()) {
            return first.get();
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    public static void addNewUser(User newUser) {
        Optional<User> first = USERS.stream()
                .filter(user -> user.getUsername().equals(newUser.getUsername()))
                .findFirst();
        if (first.isPresent()) {
            throw new RuntimeException("User already exist.");
        } else {
            USERS.add(newUser);
        }
    }

    public static void updateUser(User oldUser) {
        Optional<User> first = USERS.stream()
                .filter(user -> user.getUsername().equals(oldUser.getUsername()))
                .findFirst();
        if (first.isPresent()) {
            first.get().setAge(oldUser.age);
//            first.get().setUsername(oldUser.username);
        } else {
            throw new RuntimeException("User not found.");
        }
    }

    public static void deleteUser(String username) {
        Optional<User> first = USERS.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        if (first.isPresent()) {
            USERS.remove(first.get());
        } else {
            throw new RuntimeException("User not found.");
        }
    }
}
