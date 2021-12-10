package com.example.sampleapp2.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PropertyValue {
    @Value("${sample_app_1.base_url}")
    private String sampleApp1BaseUrl;
    @Value("${sample_app_1.get_user_by_id}")
    private String sampleApp1GetUserById;
}
