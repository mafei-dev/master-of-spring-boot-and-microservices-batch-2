package com.example.orderservice.service.external.fallback;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.model.external.UserViewModal;
import com.example.orderservice.service.external.UserServiceClientCloud;
import com.example.orderservice.util.CustomLogger;
import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserServiceClientCloudFallbackFactory implements FallbackFactory<UserServiceClientCloud> {

    private final CustomLogger customLogger;

    @Override
    public UserServiceClientCloud create(Throwable cause) {
        System.out.println("UserServiceClientCloudFallbackFactory.create");
        return new UserServiceClientCloudFallback(cause, this.customLogger);
    }

}
