package com.example.sampleapp1.dto.transfomer;

import com.example.sampleapp1.dto.request.UserRequest;
import com.example.sampleapp1.dto.response.UserResponse;
import com.example.sampleapp1.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer {
    public User convertUserRequestDtoToModel(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        return user;
    }

    public UserResponse convertModelToUserResponseDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        return userResponse;
    }
}
