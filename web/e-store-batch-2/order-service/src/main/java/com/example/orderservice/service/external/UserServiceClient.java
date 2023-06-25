package com.example.orderservice.service.external;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.model.external.CreatedUserViewModel;
import com.example.orderservice.model.external.UserViewModal;
import com.example.orderservice.util.CustomLogger;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", path = "/user")
public interface UserServiceClient {
    @GetMapping
    UserViewModal.UserViewResponse getUserByName(@RequestParam("username") String username) throws ServiceException;


    @PostMapping
    CreatedUserViewModel.Response addNewUser(@RequestBody CreatedUserViewModel.Request request);
}
