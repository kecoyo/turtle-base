package com.kecoyo.turtlebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.model.App;

public interface AppService extends IService<App> {

    App getById(Integer id, boolean throwEx);

}
