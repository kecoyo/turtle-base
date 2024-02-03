package com.kecoyo.turtleopen.domain.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户")
@TableName(value = "sys_user", autoResultMap = true)
public class User implements Serializable {

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    @Schema(description = "用户性别")
    private Integer gender;

    @Schema(description = "用户生日")
    private String birthday;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户备注")
    private String remark;

    @Schema(description = "创建时间")
    @TableField("create_at")
    private Timestamp createAt;

    @Schema(description = "更新时间")
    @TableField("update_at")
    private Timestamp updateAt;

    @Schema(description = "状态(0禁用,1启用)")
    @TableField("status")
    private Integer status;

    @Schema(description = "删除状态(0正常,1删除)")
    @TableField("deleted")
    private Integer deleted;

}
