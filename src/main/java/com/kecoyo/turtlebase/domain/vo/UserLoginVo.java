package com.kecoyo.turtlebase.domain.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserLoginVo implements Serializable {

    private Integer id;

    private String name;

    private String avatar;

    private String phone;

    private Integer gender;

    private String email;

    private String remark;

    private String token;

}
