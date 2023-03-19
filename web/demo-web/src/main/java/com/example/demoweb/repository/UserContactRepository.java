package com.example.demoweb.repository;

import com.example.demoweb.entity.UserContactEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactRepository extends CrudRepository<UserContactEntity, String> {

}
