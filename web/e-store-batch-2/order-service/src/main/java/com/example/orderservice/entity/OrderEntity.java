package com.example.orderservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "order")
@Table(name = "order_detail")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "order_id", unique = true)
    private String orderId;


    @Column(name = "order_datetime")
    private LocalDateTime orderDateTime;

    @Column(name = "username")
    private String username;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "transaction_id")
    private String paymentId;

    /*@OneToMany(mappedBy = "orderEntity")
    private List<OrderStatusEntity> status;*/
}
