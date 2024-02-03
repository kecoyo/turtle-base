package com.kecoyo.turtlebase.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kecoyo.turtlebase.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户绑定")
@TableName(value = "sys_user_bind", autoResultMap = true)
public class UserBind extends BaseEntity {

    @Schema(description = "应用ID")
    private Integer appId;

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "微信openid")
    private String openid;

    @Schema(description = "微信unionid")
    private String unionid;

}
