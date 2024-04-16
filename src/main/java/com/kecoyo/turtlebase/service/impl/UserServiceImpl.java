package com.kecoyo.turtlebase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtlebase.common.redis.RedisUtils;
import com.kecoyo.turtlebase.common.security.JwtProperties;
import com.kecoyo.turtlebase.common.security.JwtUserDto;
import com.kecoyo.turtlebase.common.security.SecurityUtils;
import com.kecoyo.turtlebase.common.security.TokenProvider;
import com.kecoyo.turtlebase.dto.LoginUserDto;
import com.kecoyo.turtlebase.mapper.UserMapper;
import com.kecoyo.turtlebase.model.User;
import com.kecoyo.turtlebase.service.UserService;
import com.kecoyo.turtlebase.service.mapstruct.UserMapstruct;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private UserMapstruct userMapstruct;

    @Autowired
    private OnlineUserServiceImpl onlineUserService;

    @Override
    public LoginUserDto login(String username, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtUserDto userDetails = (JwtUserDto) authentication.getPrincipal();
        log.info("userDetails: {}", userDetails);

        String token = tokenProvider.createToken(authentication);

        LoginUserDto userLoginVo = userMapstruct.toLoginUserDto(userDetails);
        userLoginVo.setToken(jwtProperties.getTokenStartWith() + token);

        // 保存在线信息
        onlineUserService.save(userDetails, token, request);

        return userLoginVo;
    }

    @Override
    public LoginUserDto getLoginUser() {
        JwtUserDto userDetails = SecurityUtils.getCurrentUser();
        LoginUserDto userLoginVo = userMapstruct.toLoginUserDto(userDetails);
        return userLoginVo;
    }

    @Override
    public User getByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = this.getOne(queryWrapper);
        return user;
    }

}
