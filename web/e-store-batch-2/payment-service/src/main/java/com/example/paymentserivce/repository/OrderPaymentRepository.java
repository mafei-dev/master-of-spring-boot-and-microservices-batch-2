package com.example.paymentserivce.repository;

import com.example.paymentserivce.entity.OrderPaymentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderPaymentRepository extends CrudRepository<OrderPaymentEntity, String> {

    Optional<OrderPaymentEntity> findFirstByOrderId(String orderId);

}
