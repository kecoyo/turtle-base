package com.kecoyo.turtlebase.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 */
@Configuration
@MapperScan("com.kecoyo.**.mapper")
public class MybatisPlusConfig {

}
