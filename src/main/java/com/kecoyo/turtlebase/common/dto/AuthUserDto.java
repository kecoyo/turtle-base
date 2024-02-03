package com.kecoyo.turtlebase.common.dto;

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

    @NotBlank(groups = { WxMiniLogin.class })
    private String code;

    private Integer appId;

    private String uuid = "";

    public @interface WxMiniLogin {
    }
}
