package com.example.orderservice.repository;

import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.entity.OrderStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends CrudRepository<OrderStatusEntity, String> {
}
