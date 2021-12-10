package com.example.sampleapp1.exception.handler;

import com.example.sampleapp1.dto.response.ResponseWrapper;
import com.example.sampleapp1.exception.ResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResponseExceptionHandler {
    @ExceptionHandler(value = ResponseException.class)
    public ResponseEntity<ResponseWrapper<Object>> handle(HttpServletRequest httpServletRequest,
                                                          ResponseException exception) {
        return new ResponseEntity<>(new ResponseWrapper<>(exception.getPayload(), exception.getMessage(), false),
                exception.getHttpStatus());
    }
}
