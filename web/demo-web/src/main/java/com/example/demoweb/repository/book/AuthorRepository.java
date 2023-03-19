package com.example.demoweb.repository.book;

import com.example.demoweb.entity.book.AuthorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, String> {
}
