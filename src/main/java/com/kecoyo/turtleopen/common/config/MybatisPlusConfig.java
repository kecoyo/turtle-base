package com.kecoyo.turtleopen.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@MapperScan("com.kecoyo.**.mapper")
public class MybatisPlusConfig {

}
