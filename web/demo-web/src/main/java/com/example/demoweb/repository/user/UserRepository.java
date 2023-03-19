package com.example.demoweb.repository.user;

import com.example.demoweb.entity.user.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

    //select * from user where username = ?;
    Optional<UserEntity> findByUsername(String username);

    //select * from user where username = ?;
    @Query("select myUser from user myUser where myUser.username = :username")
    Optional<UserEntity> findByUsernameByQuery(@Param("username") String username);

    List<UserEntity> findByUsernameContaining(String username);


    @Query("update user u set u.userAge = ?1 where u.username = ?2")
    @Modifying
    void updateUser(int newUserAge, String username);

}
