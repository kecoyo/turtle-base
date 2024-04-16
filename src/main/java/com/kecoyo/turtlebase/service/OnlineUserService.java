package com.kecoyo.turtlebase.service;

import com.kecoyo.turtlebase.common.security.JwtUserDto;

import jakarta.servlet.http.HttpServletRequest;

public interface OnlineUserService {

    /**
     * 保存在线用户信息
     *
     * @param jwtUserDto /
     * @param token      /
     * @param request    /
     */
    public void save(JwtUserDto jwtUserDto, String token, HttpServletRequest request);

}
