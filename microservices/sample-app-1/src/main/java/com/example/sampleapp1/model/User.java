package com.example.sampleapp1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private String id;

    private String name;
}
