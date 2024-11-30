package com.example.internintelligence_movieapidevelopment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableFeignClients
@EnableWebSecurity
//@EnableScheduling
public class InternIntelligenceMovieApiDevelopmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternIntelligenceMovieApiDevelopmentApplication.class, args);
    }

}
