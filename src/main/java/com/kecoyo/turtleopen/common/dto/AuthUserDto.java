package com.kecoyo.turtleopen.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @description 用户登录参数
 */

@Data
public class AuthUserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";
}
