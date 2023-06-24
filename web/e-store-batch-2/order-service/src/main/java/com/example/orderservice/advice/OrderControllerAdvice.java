package com.example.orderservice.advice;

import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.exception.UserNotActiveException;
import com.example.orderservice.util.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrderControllerAdvice {

    @ExceptionHandler({UserNotActiveException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse catchUserNotActiveException(UserNotActiveException exception) {
        log.error("message : {}, [username - {}]", exception.getMessage(), exception.getUsername());
        if (log.isDebugEnabled()) {
            exception.printStackTrace();
        }
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        response.put("username", exception.getUsername());
        return response;
    }

    @ExceptionHandler({OrderNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse catchOrderNotFoundException(OrderNotFoundException exception) {
        log.error("message : {}, [orderId - {}]", exception.getMessage(), exception.getOrderId());
        if (log.isDebugEnabled()) {
            exception.printStackTrace();
        }
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        response.put("order_id", exception.getOrderId());
        return response;
    }

}
