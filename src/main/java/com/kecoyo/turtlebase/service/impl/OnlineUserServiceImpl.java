package com.kecoyo.turtlebase.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kecoyo.turtlebase.common.redis.RedisUtils;
import com.kecoyo.turtlebase.common.security.JwtProperties;
import com.kecoyo.turtlebase.common.security.JwtUserDto;
import com.kecoyo.turtlebase.common.security.TokenProvider;
import com.kecoyo.turtlebase.common.utils.EncryptUtils;
import com.kecoyo.turtlebase.dto.OnlineUserDto;
import com.kecoyo.turtlebase.service.OnlineUserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zheng Jie
 * @date 2019年10月26日21:56:27
 */
@Service
@Slf4j
@AllArgsConstructor
public class OnlineUserServiceImpl implements OnlineUserService {

    @Autowired
    private JwtProperties properties;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 保存在线用户信息
     *
     * @param jwtUserDto /
     * @param token      /
     * @param request    /
     */
    public void save(JwtUserDto jwtUserDto, String token, HttpServletRequest request) {
        // String ip = StringUtils.getIp(request);
        // String browser = StringUtils.getBrowser(request);
        // String address = StringUtils.getCityInfo(ip);
        OnlineUserDto onlineUserDto = null;
        try {
            onlineUserDto = new OnlineUserDto(jwtUserDto.getUsername(), jwtUserDto.getUser().getName(), "",
                    "", "", "", EncryptUtils.desEncrypt(token), new Date());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        String loginKey = tokenProvider.loginKey(token);
        redisUtils.set(loginKey, onlineUserDto, properties.getTokenValidityInSeconds(), TimeUnit.MILLISECONDS);
    }

}
