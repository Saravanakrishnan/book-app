package com.example.bookapp.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer
{
    //@Autowired
    //MinimalInterceptor minimalInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new MinimalInterceptor());
    }
}