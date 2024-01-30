package com.kecoyo.turtleopen.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kecoyo.turtleopen.common.dto.AuthUserDto;
import com.kecoyo.turtleopen.common.web.ResponseResult;
import com.kecoyo.turtleopen.service.IAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "认证服务")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @Operation(summary = "账号登录")
    @PostMapping("/login")
    public ResponseResult<Map<String, Object>> login(@Validated @RequestBody AuthUserDto dto) {
        Map<String, Object> result = authService.login(dto.getUsername(), dto.getPassword());
        return ResponseResult.success(result);
    }

}
