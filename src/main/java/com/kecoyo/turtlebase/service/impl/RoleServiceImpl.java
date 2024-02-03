package com.kecoyo.turtlebase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtlebase.domain.entity.Role;
import com.kecoyo.turtlebase.mapper.RoleMapper;
import com.kecoyo.turtlebase.service.RoleService;
import com.kecoyo.turtlebase.service.mapstruct.BeanMapper;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private BeanMapper beanMapper;

}
