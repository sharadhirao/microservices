package com.example.sampleapp2.testdata;

import com.example.sampleapp2.dto.external.UserResponse;
import com.example.sampleapp2.model.Post;
import lombok.Getter;

@Getter
public class PostTestData {
    private Post post1;
    private UserResponse userResponse;

    public PostTestData() {
        this.post1 = new Post();
        this.post1.setId("1");
        this.post1.setContent("Sample Content");
        this.post1.setUserId("u1");


        this.userResponse = new UserResponse();
        this.userResponse.setId("u1");
        this.userResponse.setName("User 1");
    }
}
