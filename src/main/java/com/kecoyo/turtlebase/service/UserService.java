package com.kecoyo.turtlebase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.domain.User;
import com.kecoyo.turtlebase.domain.dto.UserLoginDto;
import com.kecoyo.turtlebase.domain.vo.UserLoginVo;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService extends IService<User> {

    UserLoginVo login(UserLoginDto dto, HttpServletRequest request);

    User getByUsername(String username);

    List<User> listAll();

}
