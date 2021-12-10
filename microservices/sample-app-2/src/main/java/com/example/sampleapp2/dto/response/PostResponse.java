package com.example.sampleapp2.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
    private String id;

    private String content;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userId;
}
