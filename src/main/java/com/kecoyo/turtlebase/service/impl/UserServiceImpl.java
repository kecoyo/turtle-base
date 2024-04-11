package com.kecoyo.turtlebase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtlebase.common.redis.RedisUtils;
import com.kecoyo.turtlebase.mapper.UserMapper;
import com.kecoyo.turtlebase.model.User;
import com.kecoyo.turtlebase.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    // @Cacheable(value = "userList")
    public List<User> listAll() {
        List<User> list = (List<User>) redisUtils.get("userList");
        if (list == null) {
            list = this.list();
            redisUtils.set("userList", list);
        }
        return list;
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public User findByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = this.getOne(queryWrapper);
        return user;
    }

}
