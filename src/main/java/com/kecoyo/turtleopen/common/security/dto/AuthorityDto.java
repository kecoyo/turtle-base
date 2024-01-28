package com.kecoyo.turtleopen.common.security.dto;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

/**
 * 避免序列化问题
 */
@Data
public class AuthorityDto implements GrantedAuthority {

    private String authority;
}
