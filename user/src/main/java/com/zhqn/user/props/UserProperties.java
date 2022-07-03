package com.zhqn.user.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "user")
@Data
public class UserProperties {

    @Value("${loginTokenExpireHours:24}")
    private Integer loginTokenExpireHours;

    @Value("${loginTokenSecret:ninkqwndqwdqwdubuqf}")
    private String loginTokenSecret;

}
