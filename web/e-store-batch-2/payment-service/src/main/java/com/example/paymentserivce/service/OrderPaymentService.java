package com.example.paymentserivce.service;

import com.example.paymentserivce.dto.NewOrderPaymentDTO;
import com.example.paymentserivce.dto.ViewOrderPaymentDetailDTO;
import com.example.paymentserivce.entity.OrderPaymentEntity;
import com.example.paymentserivce.exception.PaymentNotFoundException;
import com.example.paymentserivce.repository.OrderPaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderPaymentService {
    private final OrderPaymentRepository orderPaymentRepository;

    @Transactional
    public NewOrderPaymentDTO.Response addNewOrderPayment(NewOrderPaymentDTO.Request request) {
        final LocalDateTime now = LocalDateTime.now();

        OrderPaymentEntity paymentEntity = this.orderPaymentRepository.save(
                OrderPaymentEntity
                        .builder()
                        .orderPaymentId(UUID.randomUUID().toString())
                        .amount(request.getAmount())
                        .status("success")
                        .dateTime(now)
                        .username(request.getUsername())
                        .orderId(request.getOrderId())
                        .userId(request.getUserId())
                        .build()
        );
        return NewOrderPaymentDTO
                .Response
                .builder()
                .orderPaymentId(paymentEntity.getOrderPaymentId())
                .amount(paymentEntity.getAmount())
                .status(paymentEntity.getStatus())
                .dateTime(paymentEntity.getDateTime())
                .username(paymentEntity.getUsername())
                .orderId(paymentEntity.getOrderId())
                .userId(paymentEntity.getUserId())
                .build();

    }

    public ViewOrderPaymentDetailDTO.Response getPaymentDetail(String orderId) throws PaymentNotFoundException {
        return this.orderPaymentRepository
                .findFirstByOrderId(orderId)
                .map(orderPaymentEntity -> ViewOrderPaymentDetailDTO
                        .Response
                        .builder()
                        .orderPaymentId(orderPaymentEntity.getOrderPaymentId())
                        .amount(orderPaymentEntity.getAmount())
                        .status(orderPaymentEntity.getStatus())
                        .dateTime(orderPaymentEntity.getDateTime())
                        .username(orderPaymentEntity.getUsername())
                        .orderId(orderPaymentEntity.getOrderId())
                        .userId(orderPaymentEntity.getUserId())
                        .build()
                )
                .orElseThrow(() ->
                        new PaymentNotFoundException(
                                "The payment detail not found.",
                                orderId
                        )
                );
    }
}
