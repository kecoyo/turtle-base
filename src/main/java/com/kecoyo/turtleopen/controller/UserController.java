package com.kecoyo.turtleopen.controller;

import com.kecoyo.turtleopen.common.web.ResponseResult;
import com.kecoyo.turtleopen.domain.dto.UserDTO;
import com.kecoyo.turtleopen.domain.vo.SysUserVO;
import com.kecoyo.turtleopen.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "用户服务")
public class UserController {

    @Autowired
    private IUserService userService;

    @Operation(summary = "账号密码登录")
    @GetMapping("/login")
    public ResponseResult<List<SysUserVO>> login(@Validated({UserDTO.Login.class}) UserDTO dto) {
        List<SysUserVO> result = userService.login(dto.getUsername(), dto.getPassword());
        return ResponseResult.success(result);
    }

}
