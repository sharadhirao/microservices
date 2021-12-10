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
import com.example.sampleapp2.testdata.PostTestData;
import com.example.sampleapp2.utils.PropertyValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Optional;

class PostServiceImplTest {
    private final PostRepository postRepository;
    private final WebClient webClient;
    private final PostService postService;
    private final PostTestData postTestData;
    private static MockWebServer mockWebServer;
    @Autowired
    private PropertyValue propertyValue;
    private final ObjectMapper objectMapper;


    @BeforeAll
    static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }

    public PostServiceImplTest() {
        this.postRepository = Mockito.mock(PostRepository.class);
        this.webClient = WebClient.builder().defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
        PostTransformer postTransformer = new PostTransformer();
        this.propertyValue = Mockito.mock(PropertyValue.class);
        this.postTestData = new PostTestData();
        this.postService = new PostServiceImpl(postRepository, postTransformer, this.webClient, propertyValue);
        objectMapper = new ObjectMapper();
    }

    @Test
    void getPostById() {
        Post post1 = this.postTestData.getPost1();
        Mockito.when(postRepository.findById(Mockito.anyString())).thenReturn(Optional.of(post1));

        PostResponse postResponse = postService.getPostById(post1.getId());

        Assertions.assertNotNull(postResponse);
        Assertions.assertEquals("1", postResponse.getId());
    }

    @Test
    void getPostByIdWhenDoesNotExists() {
        Mockito.when(postRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseException.class, () -> postService.getPostById("1"));
    }

    @Test
    void createPostAnonymous() {
        Post post1 = this.postTestData.getPost1();
        post1.setUserId(null);
        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(post1);
        PostRequest postRequest = new PostRequest();
        postRequest.setContent(post1.getContent());

        PostResponse postResponse = postService.createPost(postRequest, true);
        Assertions.assertNotNull(postService);
        Assertions.assertEquals("1", postResponse.getId());
        Assertions.assertNull(postResponse.getUserId());
    }


    @Test
    void createPost() throws JsonProcessingException {
        Post post1 = this.postTestData.getPost1();
        PostRequest postRequest = new PostRequest();
        postRequest.setContent(post1.getContent());
        postRequest.setUserId(post1.getUserId());

        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(post1);
        int port = mockWebServer.getPort();
        Mockito.when(propertyValue.getSampleApp1BaseUrl()).thenReturn(getMockServerUrl(port));
        Mockito.when(propertyValue.getSampleApp1GetUserById()).thenReturn("/user");

        UserResponse userResponse = this.postTestData.getUserResponse();
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .addHeader("Content-Type", "application/json")
                        .setBody(objectMapper.writeValueAsString(new ResponseWrapper<>(userResponse)))
        );

        PostResponse postResponse = postService.createPost(postRequest, false);
        Assertions.assertNotNull(postResponse);
        Assertions.assertEquals("1", post1.getId());
        Assertions.assertEquals(post1.getUserId(), postResponse.getUserId());
    }

    @NotNull
    private String getMockServerUrl(int port) {
        return "http://localhost:" + port;
    }
}