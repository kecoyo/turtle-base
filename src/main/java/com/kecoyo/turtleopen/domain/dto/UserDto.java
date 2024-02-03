package com.kecoyo.turtleopen.domain.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class UserDto implements Serializable {

    private Integer id;

    private String username;

    @JSONField(serialize = false)
    private String password;

    private String name;

    private String avatar;

    private String phone;

    private Integer gender;

    private String birthday;

    private String email;

    private String remark;

    private Timestamp createAt;

    private Timestamp updateAt;

    @JSONField(serialize = false)
    private Integer status;

    @JSONField(serialize = false)
    private Integer deleted;

}
