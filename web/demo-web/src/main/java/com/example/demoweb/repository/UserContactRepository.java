package com.example.demoweb.repository;

import com.example.demoweb.db.Database;
import com.example.demoweb.entity.UserContactEntity;
import com.example.demoweb.util.DatabaseConfig;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserContactRepository {
    public void saveUserContacts(List<UserContactEntity> userContactEntityList, String userId) {
        //access the database to save a new user contact
        DataSource dataSource = DatabaseConfig.getDataSource();
        try (Connection connection = dataSource.getConnection()) {
            connection.setReadOnly(false);
            connection.setAutoCommit(false);
            System.out.println("connection-2:" + connection);
            int next = 1;
            for (UserContactEntity userContactEntity : userContactEntityList) {
                PreparedStatement preparedStatement = connection
                        .prepareStatement("INSERT INTO user_contact (`key`, value, user_id) VALUES (?,?,?)");
                preparedStatement.setString(1, userContactEntity.getKey());
                preparedStatement.setString(2, userContactEntity.getValue());
                preparedStatement.setString(3, userId);
                if (next == 2) {
                    throw new RuntimeException("something went wrong.");
                }
                if (preparedStatement.executeUpdate() < 1) {
                    throw new RuntimeException("added failed");
                } else {
                    System.out.println("user saved");
                }
                next++;
            }
            System.out.println("save " + userContactEntityList.size() + " contact(s).");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
