package com.kecoyo.turtlebase.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "应用")
@TableName("sys_app")
public class App implements Serializable {

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "微信小程序appId")
    private String wechatAppId;

    @Schema(description = "微信小程序appSecret")
    private String wechatAppSecret;

    @Schema(description = "应用描述")
    private String remark;

}
