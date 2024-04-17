package com.kecoyo.turtlebase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kecoyo.turtlebase.common.security.AnonymousAccess;
import com.kecoyo.turtlebase.common.web.ResponseResult;
import com.kecoyo.turtlebase.dto.LoginUserDto;
import com.kecoyo.turtlebase.dto.UserLoginDto;
import com.kecoyo.turtlebase.model.User;
import com.kecoyo.turtlebase.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    @AnonymousAccess
    public ResponseResult<LoginUserDto> login(@Valid @RequestBody UserLoginDto dto, HttpServletRequest request) {
        LoginUserDto result = userService.login(dto.getUsername(), dto.getPassword(), request);
        return ResponseResult.success(result);
    }

    @Operation(summary = "获取当前登录用户")
    @GetMapping("/getLoginUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseResult<LoginUserDto> getLoginUser() {
        LoginUserDto result = userService.getLoginUser();
        return ResponseResult.success(result);
    }

    // 用户管理

    @Operation(summary = "查询用户")
    @GetMapping("/list")
    @AnonymousAccess
    public ResponseResult<List<User>> list() {
        List<User> list = userService.list();
        return ResponseResult.success(list);
    }

}
