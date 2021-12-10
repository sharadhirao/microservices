package com.example.sampleapp2.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseWrapper<T> {
    private Boolean success = true;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;

    public ResponseWrapper(T payload) {
        this(payload, null);
    }

    public ResponseWrapper(T payload, String message) {
        this(payload, message, true);
    }

    public ResponseWrapper(T payload, String message, boolean success) {
        this.payload = payload;
        this.message = message;
        this.success = success;
    }
}
