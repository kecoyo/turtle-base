package com.kecoyo.turtlebase.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.dto.UserLoginDto;
import com.kecoyo.turtlebase.model.User;

public interface UserService extends IService<User> {

    UserDetails login(UserLoginDto dto);

    User getByUsername(String username);

    List<User> listAll();

}
