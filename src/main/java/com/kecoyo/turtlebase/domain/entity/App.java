package com.kecoyo.turtlebase.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kecoyo.turtlebase.common.base.BaseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "应用")
@TableName("sys_app")
public class App extends BaseEntity {

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "微信小程序appId")
    private String wechatAppId;

    @Schema(description = "微信小程序appSecret")
    private String wechatAppSecret;

    @Schema(description = "应用描述")
    private String remark;

}
