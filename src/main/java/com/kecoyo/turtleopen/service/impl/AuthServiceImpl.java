package com.kecoyo.turtleopen.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtleopen.common.security.bean.SecurityProperties;
import com.kecoyo.turtleopen.common.dto.JwtUserDto;
import com.kecoyo.turtleopen.common.security.JwtAuthenticationEntryPoint;
import com.kecoyo.turtleopen.domain.entity.User;
import com.kecoyo.turtleopen.mapper.UserMapper;
import com.kecoyo.turtleopen.service.IAuthService;

@Service
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements IAuthService {

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtAuthenticationEntryPoint.TokenProvider tokenProvider;

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Map<String, Object> login(String username, String password) {
        // UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();

        Map<String, Object> authInfo = new HashMap<String, Object>(2) {
            {
                put("token", properties.getTokenStartWith() + token);
                put("user", jwtUserDto);
            }
        };

        return authInfo;
    }

    @Override
    public User getLoginData(String username) {
        return this.getById(1);
    }

    @Override
    public Map<String, Object> tokenLogin(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tokenLogin'");
    }

    @Override
    public Map<String, Object> wxMiniLogin(String appId, String code, String encryptedData, String iv) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'wxMiniLogin'");
    }

    @Override
    public Map<String, Object> wxOauthLogin(String appId, String code, String state) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'wxOauthLogin'");
    }

    @Override
    public Map<String, Object> bindPhone(String appId, String phone, String code) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'bindPhone'");
    }
}
