package com.kecoyo.turtleopen.common.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kecoyo.turtleopen.common.web.ApiError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        ApiError apiError = ApiError.error(HttpStatus.UNAUTHORIZED.value(), "当前登录状态过期");
        Integer code = apiError.getStatus();
        String message = JSON.toJSONString(apiError);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code);
        response.getWriter().write(message);
    }
}
