package com.example.demoweb.repository.user;

import com.example.demoweb.entity.user.UserContactEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactRepository extends CrudRepository<UserContactEntity, String> {

}
