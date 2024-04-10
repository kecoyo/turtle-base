package com.kecoyo.turtlebase.common.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kecoyo.turtlebase.common.web.ResponseResult;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {

        Integer code = HttpStatus.FORBIDDEN.value();
        String message = JSON.toJSONString(ResponseResult.fail(code, "无权访问请求的资源"));

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code);
        response.getWriter().write(message);
    }
}
