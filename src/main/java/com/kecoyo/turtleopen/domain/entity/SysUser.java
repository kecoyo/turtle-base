package com.kecoyo.turtleopen.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.kecoyo.turtleopen.common.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "用户")
@TableName(value = "sys_user", autoResultMap = true)
public class SysUser extends BaseEntity {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "登录密码")
    private String password;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户手机")
    private String phone;

    @Schema(description = "所在省份")
    private String province;

    @Schema(description = "所在城市")
    private String city;

    @Schema(description = "所在区域")
    private String county;

    @Schema(description = "用户性别")
    private String gender;

    @Schema(description = "用户生日")
    private String birthday;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户备注")
    private String remark;

}
