package com.example.orderservice.repository;

import com.example.orderservice.entity.OrderEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, String> {

    @Modifying
    @Query("update order _order set _order.paymentId = ?1 where _order.orderId = ?2")
    int updateTransactionId(String paymentId, String orderId);
}
