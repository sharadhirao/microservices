package com.example.sampleapp2.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;
    private Object payload;

    public ResponseException(HttpStatus httpStatus, String message, Object payload) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
        this.payload = payload;
    }

    public ResponseException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }
}
