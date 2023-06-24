package com.example.orderservice.controller;

import com.example.orderservice.dto.CreateOrderDTO;
import com.example.orderservice.dto.OrderStatusDTO;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.exception.UserNotActiveException;
import com.example.orderservice.model.local.CreateOrderModel;
import com.example.orderservice.model.local.OrderStatusModel;
import com.example.orderservice.service.internal.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RequestMapping("/order")
@RestController
@AllArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderModel.Response createOrder(@Valid @RequestBody CreateOrderModel.Request request)
            throws UserNotActiveException, ServiceException {

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

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public OrderStatusModel.Response getOrderStatus(@RequestParam String orderId) throws OrderNotFoundException {
        return this.orderService
                .getOrderStatus(orderId)
                .map(response -> OrderStatusModel
                        .Response
                        .builder()
                        //from internal service
                        .orderDetail(
                                OrderStatusModel.Response.OrderDetail.builder()
                                        .orderId(response.getOrderId())
                                        .orderAmount(response.getOrderAmount())
                                        .orderDate(response.getOrderDate())
                                        .build()
                        )
                        //from internal service
                        .statuses(
                                response.getStatuses()
                                        .stream()
                                        .map(orderStatus -> OrderStatusModel
                                                .Response
                                                .OrderStatus
                                                .builder()
                                                .statusDatetime(orderStatus.getStatusDatetime())
                                                .status(orderStatus.getStatus())
                                                .build()
                                        ).collect(Collectors.toList())
                        )
                        //from external service
                        .userDetail(getBuildUserDetail(response))
                        //from external service
                        .transactionData(getBuildTransactionData(response))
                        .build()
                )
                .orElseThrow(() -> new OrderNotFoundException("The order not found.", orderId));
    }

    private static OrderStatusModel.Response.UserDetail getBuildUserDetail(OrderStatusDTO.Response response) {
        if (response.isUserServiceIsDone()) {
            return OrderStatusModel.Response.UserDetail.builder()
                    .username(response.getUsername())
                    .userId(response.getUserId())
                    .tel(response.getTel())
                    .deliveryAddress(null)
                    .build();
        } else {
            return null;
        }
    }

    private static OrderStatusModel.Response.TransactionData getBuildTransactionData(OrderStatusDTO.Response response) {
        if (response.isPaymentServiceIsDone()) {
            return OrderStatusModel.Response.TransactionData.builder()
                    .transactionId(response.getTransactionId())
                    .transactionStatus(response.getTransactionStatus())
                    .build();
        } else {
            return null;
        }
    }
}
