package com.example.orderservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "order_status")
@Table(name = "order_status")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "order_status_id", unique = true)
    private String orderStatusId;


    @Column(name = "updated_datetime")
    private LocalDateTime updatedDateTime;


    @Column(name = "status")
    private String status;

   /* @Column(name = "order_id")
    private String orderId;*/

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderEntity;


}
