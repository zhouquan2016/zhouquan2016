package com.zhqn.user;

import com.zhqn.user.props.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhouquan3
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(UserProperties.class)
@ComponentScan(basePackages = "com.zhqn")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
