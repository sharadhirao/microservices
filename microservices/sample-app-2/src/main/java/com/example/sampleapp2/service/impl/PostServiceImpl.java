package com.example.sampleapp2.service.impl;

import com.example.sampleapp2.dto.external.UserResponse;
import com.example.sampleapp2.dto.request.PostRequest;
import com.example.sampleapp2.dto.response.PostResponse;
import com.example.sampleapp2.dto.response.ResponseWrapper;
import com.example.sampleapp2.dto.transformer.PostTransformer;
import com.example.sampleapp2.exception.ResponseException;
import com.example.sampleapp2.model.Post;
import com.example.sampleapp2.repository.PostRepository;
import com.example.sampleapp2.service.PostService;
import com.example.sampleapp2.utils.ErrorMessages;
import com.example.sampleapp2.utils.PropertyValue;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.ConnectException;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostTransformer postTransformer;
    private final WebClient webClient;
    private final PropertyValue propertyValue;

    public PostServiceImpl(PostRepository postRepository, PostTransformer postTransformer, WebClient webClient,
                           PropertyValue propertyValue) {
        this.postRepository = postRepository;
        this.postTransformer = postTransformer;
        this.webClient = webClient;
        this.propertyValue = propertyValue;
    }

    @Override
    public PostResponse createPost(PostRequest postRequest, boolean isAnonymous) {
        Post post = postTransformer.convertPostRequestDtoToModel(postRequest, isAnonymous);
        if (!isAnonymous) {
            try {
                WebClient.ResponseSpec responseSpec = webClient.get().uri(propertyValue.getSampleApp1BaseUrl() + propertyValue.getSampleApp1GetUserById(),
                        postRequest.getUserId()).retrieve();
                Optional<ResponseWrapper<UserResponse>> responseWrapperOptional = responseSpec.bodyToMono(new ParameterizedTypeReference<ResponseWrapper<UserResponse>>() {
                }).onErrorMap(t -> t.getCause() instanceof ConnectException,
                        t -> new ResponseException(HttpStatus.INTERNAL_SERVER_ERROR,
                                ErrorMessages.CONNECTION_REFUSED_FROM_THE_REQUIRED_APP))
                        .blockOptional();
                if (responseWrapperOptional.isEmpty()) {
                    throw new ResponseException(HttpStatus.INTERNAL_SERVER_ERROR,
                            ErrorMessages.EMPTY_RESPONSE_FROM_REQUIRED_APP);
                }
                ResponseWrapper<UserResponse> userResponseResponseWrapper = responseWrapperOptional.get();
                if (!userResponseResponseWrapper.getSuccess()) {
                    throw new ResponseException(HttpStatus.INTERNAL_SERVER_ERROR,
                            ErrorMessages.UNSUCCESSFUL_RESPONSE_FROM_THE_REQUIRED_APP,
                            userResponseResponseWrapper.getMessage());
                }
            } catch (WebClientResponseException e) {
                throw new ResponseException(e.getStatusCode(),
                        ErrorMessages.UNSUCCESSFUL_RESPONSE_FROM_THE_REQUIRED_APP, e.getResponseBodyAsString());
            }
        }
        Post savedPost = postRepository.save(post);
        return postTransformer.convertModelToPostResponse(savedPost);
    }

    @Override
    public PostResponse getPostById(String id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isEmpty()) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.POST_NOT_FOUND);
        }
        return postTransformer.convertModelToPostResponse(postOptional.get());
    }
}
