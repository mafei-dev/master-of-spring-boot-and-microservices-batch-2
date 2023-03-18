package com.example.demoweb.repository;

import com.example.demoweb.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UserRepository {

    public void saveUser(Connection connection,UserEntity user) throws SQLException {
        System.out.println("connection-1:" + connection);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `user` (pk,username,age) VALUES (?,?,?)");
        preparedStatement.setString(1, user.getUserId());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setInt(3, user.getUserAge());
        if (preparedStatement.executeUpdate() < 1) {
            throw new RuntimeException("added failed");
        } else {
            System.out.println("user saved");
        }
    }
}
