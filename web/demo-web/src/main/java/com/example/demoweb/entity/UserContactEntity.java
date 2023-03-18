package com.example.demoweb.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_contact")
@Table(name = "user_contact")
@Getter
@Setter
public class UserContactEntity {
    @Id
    @Column(name = "pk")
    private String userContactId;
    @Column(name = "contact_key")
    private String contactKey;
    @Column(name = "contact_value")
    private String contactValue;
    @Column(name = "user_id")
    private String userId;
}