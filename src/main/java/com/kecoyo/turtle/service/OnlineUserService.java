package com.kecoyo.turtle.service;

import com.kecoyo.turtle.common.security.JwtUserDto;

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
