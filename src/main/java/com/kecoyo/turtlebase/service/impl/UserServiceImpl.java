package com.kecoyo.turtlebase.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtlebase.common.redis.RedisUtils;
import com.kecoyo.turtlebase.common.security.JwtProperties;
import com.kecoyo.turtlebase.common.security.LoginUserDetails;
import com.kecoyo.turtlebase.common.security.TokenProvider;
import com.kecoyo.turtlebase.dto.UserLoginDto;
import com.kecoyo.turtlebase.mapper.UserMapper;
import com.kecoyo.turtlebase.model.User;
import com.kecoyo.turtlebase.service.UserService;
import com.kecoyo.turtlebase.service.mapstruct.UserMapstruct;

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
    public UserDetails login(UserLoginDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
        log.info("userDetails: {}", userDetails);

        return userDetails;
    }

    @Override
    public User getByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = this.getOne(queryWrapper);
        return user;
    }

    @Override
    // @Cacheable(value = "userList")
    public List<User> listAll() {
        List<User> list = (List<User>) redisUtils.get("userList");
        if (list == null) {
            list = this.list();
            redisUtils.set("userList", list);
        }
        return list;
    }

}
