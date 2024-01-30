package com.kecoyo.turtleopen.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthUserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";
}
