package com.example.paymentserivce.advice;

import com.example.paymentserivce.exception.PaymentNotFoundException;
import com.example.paymentserivce.util.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrderPaymentControllerAdvice {

    @ExceptionHandler({PaymentNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse catchPaymentNotFoundException(PaymentNotFoundException exception) {
        log.error("message : {}, [orderId - {}]", exception.getMessage(), exception.getOrderId());
        if (log.isDebugEnabled()) {
            exception.printStackTrace();
        }
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        response.put("orderId", exception.getOrderId());
        return response;
    }
}
