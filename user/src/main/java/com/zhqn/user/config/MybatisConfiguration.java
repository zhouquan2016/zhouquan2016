package com.zhqn.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.zhqn.user.mapper")
public class MybatisConfiguration {

}
