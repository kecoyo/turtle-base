package com.kecoyo.turtlebase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.model.User;

public interface UserService extends IService<User> {

    List<User> listAll();

    User findByUsername(String username);

}
