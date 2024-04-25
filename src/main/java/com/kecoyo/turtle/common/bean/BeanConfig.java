package com.kecoyo.turtle.common.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件转换Pojo类的 统一配置类
 */
@Configuration
public class BeanConfig {

    @Bean
    public ProjectProperties projectProperties() {
        return new ProjectProperties();
    }

}
