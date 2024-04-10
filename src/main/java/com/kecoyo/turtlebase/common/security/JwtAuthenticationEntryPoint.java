package com.kecoyo.turtlebase.common.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kecoyo.turtlebase.common.web.ResponseResult;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        Integer code = HttpStatus.UNAUTHORIZED.value();
        String message = JSON.toJSONString(ResponseResult.fail(code, "当前登录状态过期"));

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code);
        response.getWriter().write(message);
    }
}
