package com.kecoyo.turtleopen.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtleopen.common.dto.JwtUserDto;
import com.kecoyo.turtleopen.domain.entity.User;

public interface IAuthService extends IService<User> {

    JwtUserDto login(String username, String password);

    Map<String, Object> tokenLogin(String token);

    Map<String, Object> wxMiniLogin(String appId, String code, String encryptedData, String iv);

    Map<String, Object> wxOauthLogin(String appId, String code, String state);

    Map<String, Object> bindPhone(String appId, String phone, String code);

    User getLoginData(String username);
}
