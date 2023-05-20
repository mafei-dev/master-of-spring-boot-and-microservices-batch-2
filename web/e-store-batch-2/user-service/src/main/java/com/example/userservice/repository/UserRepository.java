package com.example.userservice.repository;

import com.example.userservice.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {


    /**
     * get the user entity by the username.
     *
     * @param username the username of the user.
     * @return the user entity of the given user.
     */
    Optional<UserEntity> findFirstByUsername(String username);
}
