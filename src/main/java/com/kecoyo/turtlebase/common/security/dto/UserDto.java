package com.kecoyo.turtlebase.common.security.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import lombok.Data;

@Data
public class UserDto implements Serializable {

    private Integer id;

    private Set<RoleDto> roles;

    private String username;

    private String password;

    private String nickName;

    private String email;

    private String phone;

    private String gender;

    private String avatarName;

    private String avatarPath;

    private Boolean enabled;

    private Boolean isAdmin = false;

    private Date pwdResetTime;

    private String createBy;

    private String updateBy;

    private Timestamp createTime;

    private Timestamp updateTime;
}
