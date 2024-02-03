package com.kecoyo.turtlebase.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtlebase.domain.entity.UserBind;
import com.kecoyo.turtlebase.mapper.UserBindMapper;
import com.kecoyo.turtlebase.service.UserBindService;

@Service
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements UserBindService {

    @Override
    public UserBind getByOpenid(Integer appId, String openid) {
        UserBind userBind = this.getOne(Wrappers.<UserBind>lambdaQuery()
                .eq(UserBind::getAppId, appId)
                .eq(UserBind::getOpenid, openid));
        return userBind;
    }

}
