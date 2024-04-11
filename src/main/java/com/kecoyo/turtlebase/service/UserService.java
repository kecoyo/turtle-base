package com.kecoyo.turtlebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.model.User;

public interface UserService extends IService<User> {

    User findByUsername(String username);

}
