package com.example.sampleapp2.dto.request;

import com.example.sampleapp2.exception.ResponseException;
import com.example.sampleapp2.utils.ErrorMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest {
    @NotBlank
    private String content;
    private String userId;

    public void validateUserId(boolean isAnonymous) {
        if (!isAnonymous && (userId == null || userId.isBlank())) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, ErrorMessages.ERROR_VALIDATING_THE_FIELDS, Map.of(
                    "userId", ErrorMessages.USER_ID_MUST_BE_PRESENT));
        }
    }
}
