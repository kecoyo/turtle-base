package com.kecoyo.turtlebase.common.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 */
@Configuration
@MapperScan("com.kecoyo.**.mapper")
public class MybatisPlusConfig {

}
