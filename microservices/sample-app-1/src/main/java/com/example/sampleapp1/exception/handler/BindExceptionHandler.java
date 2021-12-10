package com.example.sampleapp1.exception.handler;

import com.example.sampleapp1.dto.response.ResponseWrapper;
import com.example.sampleapp1.utils.ErrorMessages;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class BindExceptionHandler {
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ResponseWrapper<Object>> handle(HttpServletRequest httpServletRequest,
                                                          BindException exception) {
        Map<String, String> collect = exception.getAllErrors().stream()
                .collect(Collectors.toMap(it -> ((FieldError) it).getField(),
                        DefaultMessageSourceResolvable::getDefaultMessage));
        return ResponseEntity.badRequest().body(new ResponseWrapper<>(collect,
                ErrorMessages.ERROR_VALIDATING_THE_FIELDS, false));
    }
}
