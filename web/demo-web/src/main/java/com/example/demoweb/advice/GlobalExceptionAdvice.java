package com.example.demoweb.advice;

import com.example.demoweb.exception.EmailNotFoundException;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    //EmailNotFoundException
    @ExceptionHandler({EmailNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleEmailNotFoundException(EmailNotFoundException exception) {
        System.out.println("ex: " + exception.getMessage());
        System.out.println("ex:username: " + exception.getUsername());
        System.out.println("send the error to the bugs collecting service.");
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("msg", exception.getMessage());
        errorResponse.put("username", exception.getUsername());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler({Exception.class,RuntimeException.class})
    public ResponseEntity<Map<String, Object>> handleException(Exception exception) {
        System.out.println("ex: " + exception.getMessage());
        System.out.println("send the error to the bugs collecting service.");
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("msg", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
