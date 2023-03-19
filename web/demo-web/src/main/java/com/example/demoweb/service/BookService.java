package com.example.demoweb.service;

import com.example.demoweb.dto.NewBookDetailDTO;
import com.example.demoweb.entity.book.BookEntity;
import com.example.demoweb.repository.book.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(transactionManager = "bookTransactionManager")
    public Map<String, String> saveNewBook(NewBookDetailDTO dto) {
        BookEntity save = this.bookRepository.save(
                BookEntity.builder()
                        .bookId(UUID.randomUUID().toString())
                        .bookName(dto.getBookName())
                        .build()
        );
        Map<String, String> bookId = new java.util.HashMap<>(Collections.singletonMap("book_id", save.getBookId()));
        bookId.put("book_name", save.getBookName());
        return bookId;
    }
}
