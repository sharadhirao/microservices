package com.example.sampleapp2.utils;

public interface ErrorMessages {
    String ERROR_VALIDATING_THE_FIELDS = "Error validating the fields";
    String USER_ID_MUST_BE_PRESENT = "must not be blank if anonymous query param is not set to true";
    String POST_NOT_FOUND = "Post not found";
    String UNSUCCESSFUL_RESPONSE_FROM_THE_REQUIRED_APP = "Unsuccessful response from the required app";
    String EMPTY_RESPONSE_FROM_REQUIRED_APP = "Empty response from the required app";
    String CONNECTION_REFUSED_FROM_THE_REQUIRED_APP = "Connection refused from the required app";
}
