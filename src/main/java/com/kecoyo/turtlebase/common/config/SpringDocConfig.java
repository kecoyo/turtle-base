package com.kecoyo.turtlebase.common.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Swagger3.0配置
 */

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        // 联系人信息(contact)，构建API的联系人信息，用于描述API开发者的联系信息，包括名称、URL、邮箱等
        Contact contact = new Contact()
                .name("kecoyo") // 作者名称
                .email("kecoho@gmail.com") // 作者邮箱
                .url("https://www.lexinedu.com/") // 介绍作者的URL地址
                .extensions(new HashMap<String, Object>());// 使用Map配置信息（如key为"name","email","url"）

        // 授权许可信息(license)，构建API的授权许可信息，用于描述API的许可证信息，包括名称、URL等
        License license = new License()
                .name("Apache 2.0") // 授权名称
                .url("https://www.apache.org/licenses/LICENSE-2.0.html") // 授权信息
                .identifier("Apache-2.0") // 标识授权许可
                .extensions(new HashMap<String, Object>());// 使用Map配置信息（如key为"name","url","identifier"）

        // API文档基本信息(info)，构建API的基本信息，用于描述API的基本信息，包括标题、描述、版本、服务条款等
        Info info = new Info()
                .title("LJAdmin 后台管理系统API文档") // Api接口文档标题（必填）
                .description("项目描述") // Api接口文档描述
                // .termsOfService("https://www.cnblogs.com/vic-tory/") // Api接口的服务条款地址
                // .license(license) // 授权名称
                // .contact(contact) // 设置联系人信息
                .version("1.0.0"); // Api接口版本

        // 服务器信息(servers)，构建API的服务器基本信息，用于描述API的服务器信息，包括URL、描述、变量等
        List<Server> servers = new ArrayList<>();
        servers.add(new Server().url("http://localhost:8080").description("本地服务"));
        servers.add(new Server().url("http://localhost:8081").description("线上服务"));

        // 设置 spring security jwt accessToken 认证的请求头 Authorization: Bearer xxx.xxx.xxx
        SecurityScheme securityScheme = new SecurityScheme()
                .name("JWT认证")
                .scheme("bearer") // 如果是Http类型，Scheme是必填
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(info)
                .servers(servers)
                .components(new Components().addSecuritySchemes("authScheme", securityScheme)) // 添加鉴权组件
                .addSecurityItem(new SecurityRequirement().addList("authScheme")) // 全部添加鉴权小锁
                .externalDocs(new ExternalDocumentation()); // 配置Swagger3.0描述信息

    }

}
