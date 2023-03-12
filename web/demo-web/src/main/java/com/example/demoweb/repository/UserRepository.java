package com.example.demoweb.repository;

import com.example.demoweb.db.Database;
import com.example.demoweb.entity.UserEntity;
import com.example.demoweb.util.DatabaseConfig;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UserRepository {

    public void saveUser(UserEntity user) {
        DataSource dataSource = DatabaseConfig.getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            connection.setReadOnly(false);
            System.out.println("connection-1:" + connection);
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO `user` (pk,username,age) VALUES (?,?,?)");
            preparedStatement.setString(1, user.getPk());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setInt(3, user.getAge());
            if (preparedStatement.executeUpdate() < 1) {
                throw new RuntimeException("added failed");
            } else {
                System.out.println("user saved");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
