package com.kecoyo.turtlebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.common.security.dto.LoginUserDto;
import com.kecoyo.turtlebase.model.User;

public interface AuthService extends IService<User> {

    LoginUserDto login(String username, String password);

    LoginUserDto tokenLogin(String token);

    LoginUserDto wxMiniLogin(Integer appId, String code);

    LoginUserDto wxOauthLogin(Integer appId, String code);

}
