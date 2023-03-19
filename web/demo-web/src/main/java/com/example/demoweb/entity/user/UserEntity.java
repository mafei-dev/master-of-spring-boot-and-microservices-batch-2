package com.example.demoweb.entity.user;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * user_table
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
@Table(name = "user")
@Getter
@Setter
public class UserEntity {
    @Id
    @Column(name = "pk")
    private String userId;
    @Column(name = "username")
    private String username;
    @Column(name = "age")
    private int userAge;
}
