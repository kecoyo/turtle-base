package com.kecoyo.turtle.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDto implements Serializable {

    private Integer id;

    private String username;

    private String name;

    private String avatar;

    private String phone;

    private Integer gender;

    private String email;

    private String remark;

}
