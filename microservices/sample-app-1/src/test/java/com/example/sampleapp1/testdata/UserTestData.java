package com.example.sampleapp1.testdata;

import com.example.sampleapp1.model.User;
import lombok.Getter;

@Getter
public class UserTestData {
    private User user1;

    public UserTestData() {
        this.user1 = new User();
        this.user1.setId("1");
        this.user1.setName("Test User 1");
    }
}
