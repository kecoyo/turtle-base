package com.kecoyo.turtleopen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtleopen.domain.entity.UserBind;

public interface UserBindService extends IService<UserBind> {

    UserBind getByOpenid(Integer appId, String openid);

}
