package com.kecoyo.turtlebase.common.security.dto;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 避免序列化问题
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDto implements GrantedAuthority {

    private String authority;
}
