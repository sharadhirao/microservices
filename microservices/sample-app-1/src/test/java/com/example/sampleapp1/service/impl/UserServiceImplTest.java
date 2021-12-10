package com.example.sampleapp1.service.impl;

import com.example.sampleapp1.dto.request.UserRequest;
import com.example.sampleapp1.dto.response.UserResponse;
import com.example.sampleapp1.dto.transfomer.UserTransformer;
import com.example.sampleapp1.exception.ResponseException;
import com.example.sampleapp1.model.User;
import com.example.sampleapp1.repository.UserRepository;
import com.example.sampleapp1.service.UserService;
import com.example.sampleapp1.testdata.UserTestData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class UserServiceImplTest {
    private final UserTransformer userTransformer;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserTestData userTestData;

    public UserServiceImplTest() {
        this.userRepository = Mockito.mock(UserRepository.class);
        this.userTransformer = new UserTransformer();
        this.userService = new UserServiceImpl(userRepository, userTransformer);
        this.userTestData = new UserTestData();
    }

    @Test
    void getUserById() {
        User user1 = userTestData.getUser1();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user1));

        UserResponse userResponse = userService.getUserById("1");

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals("1", user1.getId());
    }

    @Test
    void getUserByIdWhenUserDoesNotExists() {
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseException.class, () -> {
            userService.getUserById("1");
        });
    }

    @Test
    void createUser() {
        User user1 = userTestData.getUser1();
        UserRequest userRequest = new UserRequest();
        userRequest.setName(user1.getName());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user1);

        UserResponse userResponse = userService.createUser(userRequest);

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals("1", userResponse.getId());
    }
}