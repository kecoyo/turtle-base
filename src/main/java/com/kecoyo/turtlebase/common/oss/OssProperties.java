package com.kecoyo.turtlebase.common.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String regionId;

    private String endpoint;

    private String internalEndpoint;

    private Boolean internal;

    private String bucketName;

    private String domain;

}
