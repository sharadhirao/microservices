package com.example.sampleapp2.dto.transformer;

import com.example.sampleapp2.dto.request.PostRequest;
import com.example.sampleapp2.dto.response.PostResponse;
import com.example.sampleapp2.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostTransformer {
    public Post convertPostRequestDtoToModel(PostRequest postRequest, boolean isAnonymous) {
        Post post = new Post();
        post.setContent(postRequest.getContent());
        if (!isAnonymous) {
            post.setUserId(postRequest.getUserId());
        }
        return post;
    }

    public PostResponse convertModelToPostResponse(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setContent(post.getContent());
        postResponse.setUserId(post.getUserId());
        return postResponse;
    }
}
