package com.kecoyo.turtle.common.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {

    private String localDir;

    private String ossDir;

    private Integer threadCount;

    private boolean deleteLocalFile;

}
