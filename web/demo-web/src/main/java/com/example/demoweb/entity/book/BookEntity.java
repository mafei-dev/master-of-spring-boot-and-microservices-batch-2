package com.example.demoweb.entity.book;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "book")
@Table(schema = "book")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {
    @Id
    @Column(name = "book_id")
    private String bookId;
    @Column(name = "book_name")
    private String bookName;

    @Column(name = "author_id")
    private String authorId;
}
