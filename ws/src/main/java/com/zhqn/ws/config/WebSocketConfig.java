package com.zhqn.ws.config;

import com.zhqn.ws.handler.UserLoginInterceptor;
import com.zhqn.ws.handler.UserSessionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    UserSessionHandler userSessionHandler;

    @Resource
    UserLoginInterceptor userLoginInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(userSessionHandler, "/sock")
                .addInterceptors(userLoginInterceptor)
                .setAllowedOriginPatterns("*");
    }
}
