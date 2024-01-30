package com.kecoyo.turtleopen.domain.dto;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.kecoyo.turtleopen.common.base.BaseDTO;

import lombok.Data;

@Data
public class UserDto extends BaseDTO {

    private Integer id;

    private String username;

    @JSONField(serialize = false)
    private String password;

    private String name;

    private String email;

    private String phone;

    private String gender;

    private String avatar;

    @JSONField(serialize = false)
    private Boolean isAdmin = false;

    private Date pwdResetTime;

}
