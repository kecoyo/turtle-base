package com.kecoyo.turtlebase.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
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
import com.kecoyo.turtlebase.common.security.TokenProvider;
import com.kecoyo.turtlebase.common.security.dto.JwtUserDto;
import com.kecoyo.turtlebase.domain.User;
import com.kecoyo.turtlebase.domain.dto.UserLoginDto;
import com.kecoyo.turtlebase.domain.vo.UserLoginVo;
import com.kecoyo.turtlebase.mapper.UserMapper;
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
    public UserLoginVo login(UserLoginDto dto, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        log.info("principal: {}", jwtUserDto);

        UserLoginVo userLoginVo = new UserLoginVo();
        BeanUtils.copyProperties(jwtUserDto.getUser(), userLoginVo);
        userLoginVo.setToken(jwtProperties.getTokenStartWith() + token);

        // 保存在线信息
        onlineUserService.save(jwtUserDto, token, request);

        return userLoginVo;
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
