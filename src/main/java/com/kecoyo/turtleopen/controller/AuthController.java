package com.kecoyo.turtleopen.controller;

import com.kecoyo.turtleopen.common.web.ResponseResult;
import com.kecoyo.turtleopen.domain.dto.LoginDTO;
import com.kecoyo.turtleopen.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "用户服务")
public class AuthController {

    @Autowired
    private UserService userService;

    @Operation(summary = "账号密码登录")
    @PostMapping("/login")
    public ResponseResult<Map<String, Object>> login(@Validated LoginDTO dto) {
        Map<String, Object> result = userService.login(dto.getUsername(), dto.getPassword());
        return ResponseResult.success(result);
    }

}
