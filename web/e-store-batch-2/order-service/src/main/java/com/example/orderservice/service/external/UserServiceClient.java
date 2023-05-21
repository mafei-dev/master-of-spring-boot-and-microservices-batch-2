package com.example.orderservice.service.external;

import com.example.orderservice.model.external.CreatedUserViewModel;
import com.example.orderservice.model.external.UserViewModal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", path = "/user")
public interface UserServiceClient {
    @GetMapping
    UserViewModal.UserViewResponse getUserByName(@RequestParam("username") String username);

    @PostMapping
    CreatedUserViewModel.Response addNewUser(@RequestBody CreatedUserViewModel.Request request);
}
