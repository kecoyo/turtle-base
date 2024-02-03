package com.kecoyo.turtleopen.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtleopen.domain.entity.App;
import com.kecoyo.turtleopen.mapper.AppMapper;
import com.kecoyo.turtleopen.service.AppService;

@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Override
    public App getById(Integer id, boolean throwEx) {
        App app = this.getById(id);
        if (throwEx && app == null) {
            throw new RuntimeException("App not found");
        }
        return app;
    }

}
