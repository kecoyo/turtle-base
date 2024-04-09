package com.kecoyo.turtlebase.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(description = "短信")
@TableName("t_sms")
public class Sms implements Serializable {

    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "验证码")
    private String code;

    @Schema(description = "发送用户ID")
    private Integer userId;

    @Schema(description = "发送时间")
    private Date sendTime;

}
