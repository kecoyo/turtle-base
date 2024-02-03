package com.kecoyo.turtlebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.common.dto.JwtUserDto;
import com.kecoyo.turtlebase.domain.entity.User;

public interface AuthService extends IService<User> {

    JwtUserDto login(String username, String password);

    JwtUserDto tokenLogin(String token);

    JwtUserDto wxMiniLogin(Integer appId, String code);

    JwtUserDto wxOauthLogin(Integer appId, String code);

}
