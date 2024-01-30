package com.kecoyo.turtleopen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtleopen.domain.entity.User;

import java.util.Map;

public interface IAuthService extends IService<User> {

    Map<String, Object> login(String username, String password);

    Map<String, Object> tokenLogin(String token);

    Map<String, Object> wxMiniLogin(String appId, String code, String encryptedData, String iv);

    Map<String, Object> wxOauthLogin(String appId, String code, String state);

    Map<String, Object> bindPhone(String appId, String phone, String code);

    User getLoginData(String username);
}
