package com.example.paymentdemo.config;

import com.example.paymentdemo.interceptor.LoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * interceptor config
 */
@Configuration
public class InterceptConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/user/api/v1/*/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
