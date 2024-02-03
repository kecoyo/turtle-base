package com.kecoyo.turtlebase.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtlebase.domain.entity.App;
import com.kecoyo.turtlebase.mapper.AppMapper;
import com.kecoyo.turtlebase.service.AppService;

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
