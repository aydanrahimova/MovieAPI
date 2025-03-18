package com.example.internintelligence_movieapidevelopment.client.config;

import com.example.internintelligence_movieapidevelopment.client.clientException.TmdbErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new TmdbErrorDecoder();
    }
}
