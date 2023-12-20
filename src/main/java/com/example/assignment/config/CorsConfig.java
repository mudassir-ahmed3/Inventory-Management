package com.example.assignment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //CORS configuration applies to all endpoints in my Spring Boot application
                .allowedOrigins("http://localhost:3000") // Replace with your frontend URL in production
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

}
