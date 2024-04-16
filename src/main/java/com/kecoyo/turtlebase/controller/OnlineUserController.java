package com.kecoyo.turtlebase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kecoyo.turtlebase.common.security.JwtUserDto;
import com.kecoyo.turtlebase.dto.UserLoginDto;
import com.kecoyo.turtlebase.dto.LoginUserDto;
import com.kecoyo.turtlebase.model.User;
import com.kecoyo.turtlebase.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/onlineUser")
@Tag(name = "在线用户")
public class OnlineUserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ResponseEntity<LoginUserDto> login(@Validated @RequestBody UserLoginDto dto, HttpServletRequest request) {
        JwtUserDto result = (JwtUserDto) userService.login(dto);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "查询用户")
    @GetMapping("/list")
    public ResponseEntity<List<User>> list() {
        List<User> list = userService.list();
        return ResponseEntity.ok(list);
    }

}
