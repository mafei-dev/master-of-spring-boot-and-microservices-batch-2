package com.example.orderservice.controller;

import com.example.orderservice.dto.CreateOrderDTO;
import com.example.orderservice.exception.UserNotActiveException;
import com.example.orderservice.model.local.CreateOrderModel;
import com.example.orderservice.service.internal.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/order")
@RestController
@AllArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderModel.Response createOrder(@Valid @RequestBody CreateOrderModel.Request request)
            throws UserNotActiveException {

        String orderId = this.orderService.createOrder(
                CreateOrderDTO
                        .Request
                        .builder()
                        .amount(request.getAmount())
                        .username(request.getUsername())
                        .build()
        );
        return CreateOrderModel
                .Response
                .builder()
                .orderId(orderId)
                .build();
    }
}
