package com.example.sampleapp1.service;

import com.example.sampleapp1.dto.request.UserRequest;
import com.example.sampleapp1.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUserById(String id);
}
