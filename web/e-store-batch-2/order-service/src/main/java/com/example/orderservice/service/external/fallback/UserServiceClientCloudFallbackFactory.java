package com.example.orderservice.service.external.fallback;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.model.external.UserViewModal;
import com.example.orderservice.service.external.UserServiceClientCloud;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserServiceClientCloudFallbackFactory implements FallbackFactory<UserServiceClientCloud> {
    @Override
    public UserServiceClientCloud create(Throwable cause) {
        System.out.println("UserServiceClientCloudFallbackFactory.create");
        return new UserServiceClientCloud() {
            @Override
            public UserViewModal.UserViewResponse getUserByName(String username) throws ServiceException {
                System.out.println("UserServiceClientCloudFallbackFactory:create:getUserByName>" + cause.getMessage());
                System.out.println(this);
                return null;
            }
        };
    }

}
