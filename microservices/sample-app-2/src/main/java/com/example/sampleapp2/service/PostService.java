package com.example.sampleapp2.service;

import com.example.sampleapp2.dto.request.PostRequest;
import com.example.sampleapp2.dto.response.PostResponse;

public interface PostService {
    PostResponse createPost(PostRequest postRequest, boolean isAnonymous);

    PostResponse getPostById(String id);
}
