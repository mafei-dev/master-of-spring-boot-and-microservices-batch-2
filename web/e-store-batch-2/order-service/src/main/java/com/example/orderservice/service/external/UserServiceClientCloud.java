package com.example.orderservice.service.external;

import com.example.orderservice.exception.ServiceException;
import com.example.orderservice.model.external.UserViewModal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "UserServiceClientCloud", name = "user-service", path = "/order", fallback = UserServiceClientCloudFallback.class)
public interface UserServiceClientCloud {
    @GetMapping
        //UserServiceClientCloudgetUserByNameString
    UserViewModal.UserViewResponse getUserByName(@RequestParam("username") String username) throws ServiceException;


}
@Component
class UserServiceClientCloudFallback implements UserServiceClientCloud {
    @Override
    public UserViewModal.UserViewResponse getUserByName(String username) throws ServiceException {
        System.out.println("UserServiceClientCloudFallback.getUserByName");
        return null;
    }
}