package com.example.sampleapp2.repository;

import com.example.sampleapp2.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
