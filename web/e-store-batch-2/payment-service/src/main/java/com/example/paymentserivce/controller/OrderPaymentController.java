package com.example.paymentserivce.controller;

import com.example.paymentserivce.dto.NewOrderPaymentDTO;
import com.example.paymentserivce.dto.ViewOrderPaymentDetailDTO;
import com.example.paymentserivce.exception.PaymentNotFoundException;
import com.example.paymentserivce.model.NewOrderPaymentModel;
import com.example.paymentserivce.model.ViewOrderPaymentDetailModel;
import com.example.paymentserivce.service.OrderPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@RequestMapping("/order")
@AllArgsConstructor
public class OrderPaymentController {

    private final OrderPaymentService orderPaymentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ViewOrderPaymentDetailModel.Response getOrderPayment(@RequestParam("orderId") String orderId) throws PaymentNotFoundException {
        ViewOrderPaymentDetailDTO.Response paymentDetail =
                this.orderPaymentService.getPaymentDetail(orderId);
        return ViewOrderPaymentDetailModel
                .Response
                .builder()
                .orderPaymentId(paymentDetail.getOrderPaymentId())
                .amount(paymentDetail.getAmount())
                .status(paymentDetail.getStatus())
                .dateTime(paymentDetail.getDateTime())
                .username(paymentDetail.getUsername())
                .orderId(paymentDetail.getOrderId())
                .userId(paymentDetail.getUserId())
                .build();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewOrderPaymentModel.Response addNewOrderPayment(@RequestBody NewOrderPaymentModel.Request request) {

        NewOrderPaymentDTO.Response response = this.orderPaymentService.addNewOrderPayment(
                NewOrderPaymentDTO
                        .Request
                        .builder()
                        .amount(request.getAmount())
                        .status(request.getStatus())
                        .username(request.getUsername())
                        .orderId(request.getOrderId())
                        .userId(request.getUserId())
                        .build()
        );
        return NewOrderPaymentModel
                .Response
                .builder()
                .orderPaymentId(response.getOrderPaymentId())
                .amount(response.getAmount())
                .status(response.getStatus())
                .dateTime(response.getDateTime())
                .username(response.getUsername())
                .orderId(response.getOrderId())
                .userId(response.getUserId())
                .build();

    }


}
