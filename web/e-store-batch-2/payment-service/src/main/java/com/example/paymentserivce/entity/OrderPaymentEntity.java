package com.example.paymentserivce.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "order_payment")
@Table(name = "order_payment")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPaymentEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "order_payment_id", unique = true)
    private String orderPaymentId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    private String status;

    @Column(name = "updated_datetime")
    private LocalDateTime dateTime;

    @Column(name = "username")
    private String username;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "user_id")
    private String userId;
}
