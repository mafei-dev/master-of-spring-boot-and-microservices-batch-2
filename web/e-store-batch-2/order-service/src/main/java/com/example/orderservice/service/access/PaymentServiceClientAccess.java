package com.example.orderservice.service.access;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.model.external.NewOrderPaymentModel;
import com.example.orderservice.model.external.ViewOrderPaymentDetailModel;
import com.example.orderservice.service.external.PaymentServiceClient;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentServiceClientAccess implements PaymentServiceClient {
    //primary
    private final PaymentServiceClient paymentServiceClient;

    @Override
    @CircuitBreaker(name = "PaymentServiceClientAccessGetOrderPayment", fallbackMethod = "getOrderPaymentFallbackMethod")
    public ViewOrderPaymentDetailModel.Response getOrderPayment(String orderId) throws ServiceException {
        return paymentServiceClient.getOrderPayment(orderId);
    }

    public ViewOrderPaymentDetailModel.Response getOrderPaymentFallbackMethod(String orderId, Exception exception) throws ServiceException {
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

    @Override
    public NewOrderPaymentModel.Response addNewOrderPayment(NewOrderPaymentModel.Request request) {
        return paymentServiceClient.addNewOrderPayment(request);
    }
}
