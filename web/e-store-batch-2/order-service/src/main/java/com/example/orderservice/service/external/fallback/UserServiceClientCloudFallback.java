package com.example.orderservice.service.external.fallback;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.model.external.UserViewModal;
import com.example.orderservice.service.external.UserServiceClientCloud;
import com.example.orderservice.util.CustomLogger;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserServiceClientCloudFallback implements UserServiceClientCloud {
    private final Throwable cause;
    private final CustomLogger customLogger;

    @Override
    public UserViewModal.UserViewResponse getUserByName(String username) throws ServiceException {
        System.out.println("UserServiceClientCloudFallback:getUserByName>" + cause.getMessage());
        System.out.println(this);
        customLogger.log(cause.getMessage());
        return null;
    }
}
