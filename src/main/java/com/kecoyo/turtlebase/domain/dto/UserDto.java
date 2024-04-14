package com.kecoyo.turtlebase.domain.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserDto implements Serializable {

    private Integer id;

    private String username;

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

    private Integer status;

    private Integer deleted;

}
