package com.kecoyo.turtleopen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtleopen.domain.entity.Role;
import com.kecoyo.turtleopen.mapper.RoleMapper;
import com.kecoyo.turtleopen.service.RoleService;
import com.kecoyo.turtleopen.service.mapstruct.BeanMapper;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private BeanMapper beanMapper;

}
