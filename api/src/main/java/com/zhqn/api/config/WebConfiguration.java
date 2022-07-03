package com.zhqn.api.config;

import com.zhqn.api.intecept.UserInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@ConditionalOnClass(HandlerInterceptor.class)
public class WebConfiguration implements WebMvcConfigurer {

    public WebConfiguration() {
        System.out.println("------------webmvc configuration loading------------------");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor()).addPathPatterns();
    }

    @Bean
    HandlerInterceptor userInterceptor() {
        return new UserInterceptor();
    }
}
