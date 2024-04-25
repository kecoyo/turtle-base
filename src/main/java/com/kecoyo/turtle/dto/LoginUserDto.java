package com.kecoyo.turtle.dto;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class LoginUserDto extends UserDto {

    private String token;

    private List<Integer> dataScopes;

    private List<GrantedAuthority> authorities;

}
