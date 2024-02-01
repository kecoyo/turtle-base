package com.kecoyo.turtleopen.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtleopen.common.exception.EntityNotFoundException;
import com.kecoyo.turtleopen.domain.dto.UserLoginDto;
import com.kecoyo.turtleopen.domain.entity.User;
import com.kecoyo.turtleopen.mapper.UserMapper;
import com.kecoyo.turtleopen.service.IUserService;
import com.kecoyo.turtleopen.service.mapstruct.BeanMapper;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public UserLoginDto getLoginData(String username) {
        User user = this.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "username", username);
        } else {
            return beanMapper.toUserLoginDto(user);
        }
    }

    @Override
    public User findByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = this.getOne(queryWrapper);
        return user;
    }

}
