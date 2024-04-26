package com.kecoyo.turtle.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtle.mapper.RoleMapper;
import com.kecoyo.turtle.model.Role;
import com.kecoyo.turtle.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
