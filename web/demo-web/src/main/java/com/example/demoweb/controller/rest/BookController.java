package com.example.demoweb.controller.rest;

import com.example.demoweb.dto.NewBookDetailDTO;
import com.example.demoweb.dto.NewUserDetailDTO;
import com.example.demoweb.exception.EmailNotFoundException;
import com.example.demoweb.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(path = "/book")
//REST
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public Object addUser(HttpEntity<NewBookDetailDTO> entity) {
        return this.bookService.saveNewBook(Objects.requireNonNull(entity.getBody()));
    }
}
