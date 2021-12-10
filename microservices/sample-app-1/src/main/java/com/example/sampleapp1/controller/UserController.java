package com.example.sampleapp1.controller;

import com.example.sampleapp1.dto.request.UserRequest;
import com.example.sampleapp1.dto.response.ResponseWrapper;
import com.example.sampleapp1.dto.response.UserResponse;
import com.example.sampleapp1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        return ResponseEntity.ok(new ResponseWrapper<>(userResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<UserResponse>> getUserById(@PathVariable String id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(userResponse));
    }
}
