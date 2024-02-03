package com.kecoyo.turtlebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.domain.entity.User;

public interface UserService extends IService<User> {

    User findByUsername(String username);

    User createUser(User user);

    void updateUser(User user);

    void deleteUser(Integer id);

    void changePassword(String username, String oldPassword, String newPassword);

}
