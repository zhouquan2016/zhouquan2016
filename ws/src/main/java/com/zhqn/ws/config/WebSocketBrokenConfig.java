package com.zhqn.ws.config;

import com.zhqn.ws.handler.UserLoginInterceptor;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.Resource;

/**
 * @author zhouquan3
 */
//@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokenConfig implements WebSocketMessageBrokerConfigurer {

  @Resource
  UserLoginInterceptor userLoginInterceptor;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("sock")
            .addInterceptors(userLoginInterceptor)
            .setAllowedOriginPatterns("*");
  }



}