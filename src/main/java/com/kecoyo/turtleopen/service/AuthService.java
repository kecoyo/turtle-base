package com.kecoyo.turtleopen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtleopen.common.dto.JwtUserDto;
import com.kecoyo.turtleopen.domain.entity.User;

public interface AuthService extends IService<User> {

    JwtUserDto login(String username, String password);

    JwtUserDto tokenLogin(String token);

    JwtUserDto wxMiniLogin(Integer appId, String code);

    JwtUserDto wxOauthLogin(Integer appId, String code);

}
