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
    @CircuitBreaker(name = "cb1", fallbackMethod = "getOrderPaymentFallbackMethod")
    ViewOrderPaymentDetailModel.Response getOrderPayment(@RequestParam("orderId") String orderId) throws ServiceException;

    default ViewOrderPaymentDetailModel.Response getOrderPaymentFallbackMethod(String orderId, Exception exception) throws ServiceException {
        System.out.println("PaymentServiceClient.getOrderPaymentFallbackMethod");
        if (exception instanceof FeignException) {
            FeignException feignException = (FeignException) exception;
            System.out.println("FeignException");
            if (feignException.status() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
                System.out.println("SERVICE_UNAVAILABLE");
            }
            throw new ServiceException("FeignException", exception);
        } else if (exception instanceof CallNotPermittedException) {
            System.out.println("FeignException");
            throw new ServiceException("CallNotPermittedException", exception);
        } else {
            throw new ServiceException("UNKNOWN exception", exception);
        }
    }

    @PostMapping
    NewOrderPaymentModel.Response addNewOrderPayment(@RequestBody NewOrderPaymentModel.Request request);
}
