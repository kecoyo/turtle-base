package com.kecoyo.turtlebase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kecoyo.turtlebase.common.security.dto.AuthUserDto;
import com.kecoyo.turtlebase.common.security.dto.JwtUserDto;
import com.kecoyo.turtlebase.common.security.dto.LoginUserDto;
import com.kecoyo.turtlebase.common.web.ResponseResult;
import com.kecoyo.turtlebase.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@Tag(name = "认证服务")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "账号登录")
    @PostMapping("/login")
    public ResponseEntity<LoginUserDto> login(@Validated @RequestBody AuthUserDto dto) {
        LoginUserDto result = authService.login(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "令牌登录")
    @PostMapping("/tokenLogin")
    public ResponseEntity<LoginUserDto> tokenLogin(HttpServletRequest request, HttpServletResponse response) {
        JwtUserDto user = authService.tokenLogin("");
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "微信小程序登录")
    @PostMapping("/wxMiniLogin")
    public ResponseEntity<LoginUserDto> wxMiniLogin(
            @Validated({ AuthUserDto.WxMiniLogin.class }) @RequestBody AuthUserDto dto) {
        JwtUserDto user = authService.wxMiniLogin(dto.getAppId(), dto.getCode());
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "微信开放认证登录")
    @PostMapping("/wxOauthLogin")
    public ResponseEntity<LoginUserDto> wxOauthLogin(@Validated @RequestBody AuthUserDto dto) {
        JwtUserDto user = authService.wxOauthLogin(dto.getAppId(), dto.getCode());
        return ResponseEntity.ok(user);
    }
}
