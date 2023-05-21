package com.example.userservice.advice;

import com.example.userservice.exception.UserAlreadyExistException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.util.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class UserControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse catchUserNotFoundException(UserNotFoundException exception) {
        log.error("message : {}, [{}]", exception.getMessage(), exception.getUsername());
        if (log.isDebugEnabled()) {
            exception.printStackTrace();
        }
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        response.put("username", exception.getUsername());
        return response;
    }

    @ExceptionHandler({UserAlreadyExistException.class})
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    public ErrorResponse catchUserAlreadyExistException(UserAlreadyExistException exception) {
        log.error("message : {}, [{}]", exception.getMessage(), exception.getUsername());
        if (log.isDebugEnabled()) {
            exception.printStackTrace();
        }
        ErrorResponse response = new ErrorResponse(exception.getMessage());
        response.put("username", exception.getUsername());
        return response;
    }
}
