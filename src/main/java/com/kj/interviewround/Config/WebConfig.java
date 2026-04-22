package com.kj.interviewround.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kj.interviewround.JWT.JwtAuthenticationInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationInterceptor jwtAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthenticationInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/login", "/api/admin/register");
    }
}
