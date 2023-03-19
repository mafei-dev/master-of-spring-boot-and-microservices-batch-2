package com.example.demoweb.entity.book;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "BookAuthor")
@Table(schema = "book_author")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorEntity {

    @Id
    @Column(name = "author_id")
    private String authorId;

    @Column(name = "author_name")
    private String authorName;


}
