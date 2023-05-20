package com.example.userservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "user")
@Table(name = "user_detail")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "tel")
    private String tel;

    @Column(name = "is_active")
    private boolean isActive;

}
