package com.kecoyo.turtleopen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtleopen.domain.entity.App;

public interface AppService extends IService<App> {

    App getById(Integer id, boolean throwEx);

}
