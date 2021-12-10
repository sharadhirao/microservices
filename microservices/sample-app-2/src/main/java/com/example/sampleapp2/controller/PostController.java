package com.example.sampleapp2.controller;

import com.example.sampleapp2.dto.request.PostRequest;
import com.example.sampleapp2.dto.response.PostResponse;
import com.example.sampleapp2.dto.response.ResponseWrapper;
import com.example.sampleapp2.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<PostResponse>> createPost(@Valid @RequestBody PostRequest postRequest,
                                                                    @RequestParam(name = "anonymous",
                                                                            defaultValue = "false") Boolean isAnonymous) {
        postRequest.validateUserId(isAnonymous);
        PostResponse postResponse = postService.createPost(postRequest, isAnonymous);
        return ResponseEntity.ok(new ResponseWrapper<>(postResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<PostResponse>> getPostById(@PathVariable String id) {
        PostResponse postResponse = postService.getPostById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(postResponse));
    }
}
