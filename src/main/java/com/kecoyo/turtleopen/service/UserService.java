package com.kecoyo.turtleopen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtleopen.domain.entity.SysUser;

import java.util.Map;

public interface UserService extends IService<SysUser> {

    Map<String, Object> login(String username, String password);

    SysUser getLoginData(String username);
}
