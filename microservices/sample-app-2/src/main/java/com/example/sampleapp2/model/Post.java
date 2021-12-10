package com.example.sampleapp2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "posts")
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    private String id;

    private String content;

    private String userId;
}
