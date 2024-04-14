package com.kecoyo.turtlebase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kecoyo.turtlebase.domain.User;
import com.kecoyo.turtlebase.domain.dto.UserLoginDto;
import com.kecoyo.turtlebase.domain.vo.UserLoginVo;
import com.kecoyo.turtlebase.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Tag(name = "用户服务")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ResponseEntity<UserLoginVo> login(@Validated @RequestBody UserLoginDto dto, HttpServletRequest request) {
        UserLoginVo result = userService.login(dto, null);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "查询用户")
    @GetMapping("/list")
    public ResponseEntity<List<User>> list() {
        List<User> list = userService.list();
        return ResponseEntity.ok(list);
    }

}
