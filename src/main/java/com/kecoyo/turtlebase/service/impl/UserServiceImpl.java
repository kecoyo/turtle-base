package com.kecoyo.turtlebase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.aliyun.oss.internal.OSSUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtlebase.common.oss.OssUtils;
import com.kecoyo.turtlebase.common.redis.RedisUtils;
import com.kecoyo.turtlebase.domain.model.User;
import com.kecoyo.turtlebase.mapper.UserMapper;
import com.kecoyo.turtlebase.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private OssUtils ossUtils;

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

    @Override
    public void downloadFile() {
        ossUtils.downloadFile("pst/jingshibei/0dd756d7503c4b72ead52c8c62246fd0.pdf",
                "d:/pst/jingshibei/0dd756d7503c4b72ead52c8c62246fd0.pdf");
    }

}
