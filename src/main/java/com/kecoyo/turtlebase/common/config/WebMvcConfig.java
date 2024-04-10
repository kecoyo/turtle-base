package com.kecoyo.turtlebase.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

/**
 * 为 RestController 接口配置统一前缀
 */
@Slf4j
@Configuration
// @EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${spring.context-path}")
    private String contextPath;

    @Override
    public void configurePathMatch(@NonNull PathMatchConfigurer configurer) {
        if (StringUtils.isNotBlank(contextPath)) {
            configurer.addPathPrefix(contextPath, c -> c.isAnnotationPresent(RestController.class));
        }
    }

}
