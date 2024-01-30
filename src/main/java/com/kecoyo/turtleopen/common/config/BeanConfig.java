package com.kecoyo.turtleopen.common.config;

import com.kecoyo.turtleopen.common.security.bean.JwtProperties;
import com.kecoyo.turtleopen.common.security.bean.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件转换Pojo类的 统一配置类
 */
@Configuration
public class BeanConfig {

    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public JwtProperties jwtProperties() {
        return new JwtProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "jwt2")
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }
}
