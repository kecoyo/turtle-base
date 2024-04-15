package com.kecoyo.turtlebase.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kecoyo.turtlebase.common.security.LoginUserDetails;
import com.kecoyo.turtlebase.common.security.SecurityUtils;
import com.kecoyo.turtlebase.common.web.ResponseResult;
import com.kecoyo.turtlebase.dto.UserLoginDto;
import com.kecoyo.turtlebase.model.User;
import com.kecoyo.turtlebase.service.UserService;
import com.kecoyo.turtlebase.vo.UserLoginVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
@Tag(name = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public ResponseResult<UserLoginVo> login(@Validated @RequestBody UserLoginDto dto, HttpServletRequest request) {
        UserLoginVo result = userService.login(dto);

        String token = tokenProvider.createToken(authentication);

        UserLoginVo userLoginVo = new UserLoginVo();
        BeanUtils.copyProperties(userDetails.getUser(), userLoginVo);
        userLoginVo.setToken(jwtProperties.getTokenStartWith() + token);

        // 保存在线信息
        onlineUserService.save(userDetails, token, request);

        return ResponseResult.success(result);
    }

    @Operation(summary = "获取当前登录用户")
    @GetMapping("/getLoginUser")
    public ResponseResult<LoginUserDetails> getLoginUser() {
        LoginUserDetails currentUser = SecurityUtils.getCurrentUser();
        return ResponseResult.success(currentUser);
    }

    @Operation(summary = "查询用户")
    @GetMapping("/list")
    public ResponseResult<List<User>> list() {
        List<User> list = userService.list();
        return ResponseResult.success(list);
    }

}
