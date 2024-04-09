package com.kecoyo.turtlebase.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "用户绑定")
@TableName("sys_user_bind")
public class UserBind implements Serializable {

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "应用ID")
    private Integer appId;

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "微信openid")
    private String openid;

    @Schema(description = "微信unionid")
    private String unionid;

}
