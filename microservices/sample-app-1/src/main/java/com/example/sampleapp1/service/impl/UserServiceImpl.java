package com.example.sampleapp1.service.impl;

import com.example.sampleapp1.dto.request.UserRequest;
import com.example.sampleapp1.dto.response.UserResponse;
import com.example.sampleapp1.dto.transfomer.UserTransformer;
import com.example.sampleapp1.exception.ResponseException;
import com.example.sampleapp1.model.User;
import com.example.sampleapp1.repository.UserRepository;
import com.example.sampleapp1.service.UserService;
import com.example.sampleapp1.utils.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserTransformer userTransformer;

    public UserServiceImpl(UserRepository userRepository, UserTransformer userTransformer) {
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = userTransformer.convertUserRequestDtoToModel(userRequest);
        User savedUser = userRepository.save(user);
        return userTransformer.convertModelToUserResponseDto(savedUser);
    }

    @Override
    public UserResponse getUserById(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.USER_NOT_FOUND);
        }
        return userTransformer.convertModelToUserResponseDto(userOptional.get());
    }
}
