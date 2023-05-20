package com.example.userservice.service;

import com.example.userservice.dto.UserCreateDTO;
import com.example.userservice.dto.UserViewDTO;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.exception.UserAlreadyExistException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public List<UserCreateDTO.UserCreateResponse> save(UserCreateDTO.UserCreateRequest[] userCreateRequests) throws UserAlreadyExistException {
        List<UserCreateDTO.UserCreateResponse> responseList = new ArrayList<>();
        for (UserCreateDTO.UserCreateRequest userCreateRequest : userCreateRequests) {
            if (this.userRepository
                    .findFirstByUsername(userCreateRequest.getUsername())
                    .isPresent()) {
                throw new UserAlreadyExistException("User already exist.", userCreateRequest.getUsername());
            } else {
                String userId = UUID.randomUUID().toString();
                this.userRepository.save(
                        UserEntity.builder()
                                .userId(userId)
                                .tel(userCreateRequest.getTel())
                                .isActive(userCreateRequest.isActive())
                                .username(userCreateRequest.getUsername())
                                .build()
                );

                responseList.add(UserCreateDTO
                        .UserCreateResponse
                        .builder()
                        .userId(userId)
                        .tel(userCreateRequest.getTel())
                        .isActive(userCreateRequest.isActive())
                        .username(userCreateRequest.getUsername())
                        .build()
                );
            }
        }

        return responseList;
    }

    public UserViewDTO.UserViewResponse getUserDetails(String username) throws UserNotFoundException {
        return this.userRepository
                .findFirstByUsername(username)
                .map(userEntity -> UserViewDTO.UserViewResponse
                        .builder()
                        .userId(userEntity.getUserId())
                        .tel(userEntity.getTel())
                        .isActive(userEntity.isActive())
                        .username(userEntity.getUsername())
                        .build()
                )
                .orElseThrow(() -> new UserNotFoundException("user doesnt exist"));
    }
}
