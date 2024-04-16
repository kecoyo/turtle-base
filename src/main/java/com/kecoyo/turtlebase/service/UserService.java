package com.kecoyo.turtlebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.dto.LoginUserDto;
import com.kecoyo.turtlebase.model.User;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @param request
     *
     * @return
     */
    LoginUserDto login(String username, String password, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @return
     */
    LoginUserDto getLoginUser();

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    User getByUsername(String username);

}
