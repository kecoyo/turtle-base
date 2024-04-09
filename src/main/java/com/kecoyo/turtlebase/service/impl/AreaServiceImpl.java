package com.kecoyo.turtlebase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtlebase.model.Area;
import com.kecoyo.turtlebase.mapper.AreaMapper;
import com.kecoyo.turtlebase.service.AreaService;
import org.springframework.stereotype.Service;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

}
