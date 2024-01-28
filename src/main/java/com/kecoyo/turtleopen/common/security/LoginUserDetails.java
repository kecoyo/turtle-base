package com.kecoyo.turtleopen.common.security;

import java.util.List;
import java.util.Set;

import com.kecoyo.turtleopen.common.security.dto.AuthorityDto;
import com.kecoyo.turtleopen.common.security.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class LoginUserDetails implements UserDetails {

    private UserDto user;

    private List<Integer> dataScopes;

    private List<AuthorityDto> authorities;

    private Set<String> roles;

    @Override
    @JSONField(serialize = false)
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JSONField(serialize = false)
    public String getUsername() {
        return user.getUsername();
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
