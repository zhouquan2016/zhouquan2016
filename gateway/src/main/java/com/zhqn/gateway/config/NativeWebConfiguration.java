package com.zhqn.gateway.config;

import com.zhqn.gateway.filter.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class NativeWebConfiguration {

    public NativeWebConfiguration() {
        System.out.println("---------------------NativeWebConfiguration---------------------------");
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");  // 允许的Method
        config.addAllowedOrigin("*");  // 允许的来源
        config.addAllowedHeader("*");  // 允许的请求头参数

        // 允许访问的资源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter();
    }
}
