package com.kecoyo.turtlebase.common.security.dto;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.fastjson.annotation.JSONField;
import com.kecoyo.turtlebase.domain.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserDto implements UserDetails {

    private User user;

    private List<Long> dataScopes;

    private List<GrantedAuthority> authorities;

    @JSONField(serialize = false)
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return user.getPassword();
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

    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return user.getStatus() == 1;
    }
}
