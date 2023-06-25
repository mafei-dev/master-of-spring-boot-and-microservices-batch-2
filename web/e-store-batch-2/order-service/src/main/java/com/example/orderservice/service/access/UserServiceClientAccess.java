package com.example.orderservice.service.access;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.model.external.CreatedUserViewModel;
import com.example.orderservice.model.external.UserViewModal;
import com.example.orderservice.service.external.UserServiceClient;
import com.example.orderservice.util.CustomLogger;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserServiceClientAccess implements UserServiceClient {

    private final UserServiceClient userServiceClient;
    private final CustomLogger customLogger;

    public UserServiceClientAccess(
//            @Qualifier("com.example.orderservice.service.external.UserServiceClient")
            UserServiceClient userServiceClient, CustomLogger customLogger) {
        this.userServiceClient = userServiceClient;
        this.customLogger = customLogger;
    }

    @Override
    @CircuitBreaker(name = "UserServiceClientAccessGetUserByName", fallbackMethod = "getUserByNameFallback")
    public UserViewModal.UserViewResponse getUserByName(String username) throws ServiceException {
        return userServiceClient.getUserByName(username);
    }

    /**
     * @param username
     * @param exception
     * @return
     * @throws ServiceException
     * @see #getUserByName(String)
     */
    public UserViewModal.UserViewResponse getUserByNameFallback(String username, Exception exception) throws ServiceException {
        System.out.println("UserServiceClientAccess.getUserByNameFallback");
        this.customLogger.log(exception.getMessage());
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
    public CreatedUserViewModel.Response addNewUser(CreatedUserViewModel.Request request) {
        return userServiceClient.addNewUser(request);
    }
}