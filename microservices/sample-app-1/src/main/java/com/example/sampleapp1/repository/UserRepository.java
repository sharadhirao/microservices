package com.example.sampleapp1.repository;

import com.example.sampleapp1.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
