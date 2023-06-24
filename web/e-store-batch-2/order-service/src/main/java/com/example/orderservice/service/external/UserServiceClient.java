package com.example.orderservice.service.external;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.model.external.CreatedUserViewModel;
import com.example.orderservice.model.external.UserViewModal;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", path = "/user")
public interface UserServiceClient {
    @GetMapping
    @CircuitBreaker(name = "cb1", fallbackMethod = "getUserByNameFallback")
    UserViewModal.UserViewResponse getUserByName(@RequestParam("username") String username) throws ServiceException;

    default UserViewModal.UserViewResponse getUserByNameFallback(String username, Exception exception) throws ServiceException {
        System.out.println("UserServiceClient.getUserByNameFallback");
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
    CreatedUserViewModel.Response addNewUser(@RequestBody CreatedUserViewModel.Request request);
}
