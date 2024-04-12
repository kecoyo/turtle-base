package com.kecoyo.turtlebase.common.oss;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;

@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssConfig {

    /**
     * 创建OSSClient实例
     */
    @Bean
    public OSSClient ossClient(OssProperties properties) {
        String endpoint = properties.getInternal() ? properties.getInternalEndpoint() : properties.getEndpoint();
        OSSClient ossClient = (OSSClient) new OSSClientBuilder().build(endpoint, properties.getAccessKeyId(),
                properties.getAccessKeySecret());
        return ossClient;
    }

}
