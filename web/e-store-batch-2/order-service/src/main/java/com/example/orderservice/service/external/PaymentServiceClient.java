package com.example.orderservice.service.external;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.model.external.NewOrderPaymentModel;
import com.example.orderservice.model.external.ViewOrderPaymentDetailModel;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payment-service", path = "/order")
public interface PaymentServiceClient {

    @GetMapping
    ViewOrderPaymentDetailModel.Response getOrderPayment(@RequestParam("orderId") String orderId) throws ServiceException;


    @PostMapping
    NewOrderPaymentModel.Response addNewOrderPayment(@RequestBody NewOrderPaymentModel.Request request);
}
