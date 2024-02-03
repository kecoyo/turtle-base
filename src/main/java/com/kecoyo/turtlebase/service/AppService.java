package com.kecoyo.turtlebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.domain.entity.App;

public interface AppService extends IService<App> {

    App getById(Integer id, boolean throwEx);

}
