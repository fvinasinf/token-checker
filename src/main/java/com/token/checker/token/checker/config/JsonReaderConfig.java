package com.token.checker.token.checker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JsonReaderConfig {
    
    @Bean
    public ObjectMapper jsonReader() {
        return new ObjectMapper();
    }

}
