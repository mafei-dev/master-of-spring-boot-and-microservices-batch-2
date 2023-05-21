package com.example.userservice.controller;

import com.example.userservice.dto.UserCreateDTO;
import com.example.userservice.dto.UserViewDTO;
import com.example.userservice.exception.UserAlreadyExistException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.CreatedUserViewModel;
import com.example.userservice.model.UserViewModal;
import com.example.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
        public UserViewModal.UserViewResponse getUserByName(@RequestParam("username") String username) throws UserNotFoundException {
        UserViewDTO.UserViewResponse userDetail = this.userService.getUserDetails(username);
        return UserViewModal.UserViewResponse.builder()
                .userId(userDetail.getUserId())
                .tel(userDetail.getTel())
                .isActive(userDetail.isActive())
                .username(userDetail.getUsername())
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedUserViewModel.Response addNewUser(@RequestBody CreatedUserViewModel.Request request) throws UserAlreadyExistException {
        return this.userService
                .save(new UserCreateDTO.UserCreateRequest[]{
                        UserCreateDTO
                                .UserCreateRequest
                                .builder()
                                .isActive(request.isActive())
                                .username(request.getUsername())
                                .tel(request.getTel())
                                .build()
                })
                .stream()
                .map(userCreateResponse -> CreatedUserViewModel
                        .Response
                        .builder()
                        .isActive(userCreateResponse.isActive())
                        .username(userCreateResponse.getUsername())
                        .tel(userCreateResponse.getTel())
                        .userId(userCreateResponse.getUserId())
                        .build()
                )
                .collect(Collectors.toList())
                .get(0);
    }


}
