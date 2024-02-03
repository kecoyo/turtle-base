package com.kecoyo.turtlebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.domain.entity.UserBind;

public interface UserBindService extends IService<UserBind> {

    UserBind getByOpenid(Integer appId, String openid);

}
