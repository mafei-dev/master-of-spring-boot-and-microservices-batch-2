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
    public void saveUserContacts(Connection connection, List<UserContactEntity> userContactEntityList, String userId) throws SQLException {
        //access the database to save a new user contact
        System.out.println("connection-2:" + connection);
        for (UserContactEntity userContactEntity : userContactEntityList) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user_contact (`key`, value, user_id) VALUES (?,?,?)");
            preparedStatement.setString(1, userContactEntity.getKey());
            preparedStatement.setString(2, userContactEntity.getValue());
            preparedStatement.setString(3, userId);
            if (preparedStatement.executeUpdate() < 1) {
                throw new RuntimeException("added failed");
            } else {
                System.out.println("user saved");
            }
        }
        System.out.println("save " + userContactEntityList.size() + " contact(s).");
    }

}
