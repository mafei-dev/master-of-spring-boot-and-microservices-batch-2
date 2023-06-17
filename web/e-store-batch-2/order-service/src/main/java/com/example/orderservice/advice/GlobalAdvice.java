package com.example.orderservice.advice;

import com.example.orderservice.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse catchMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ErrorResponse response = new ErrorResponse("Bean validation error.");
        exception.getBindingResult()
                .getAllErrors()
                .forEach(objectError -> {
                    if (objectError instanceof FieldError) {
                        FieldError fieldError = (FieldError) objectError;
                        response.put(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                });
        return response;
    }
}
