package com.kecoyo.turtleopen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtleopen.domain.dto.UserDTO;
import com.kecoyo.turtleopen.domain.entity.SysUser;
import com.kecoyo.turtleopen.domain.vo.SysUserVO;
import com.kecoyo.turtleopen.mapper.UserMapper;
import com.kecoyo.turtleopen.service.IUserService;
import com.kecoyo.turtleopen.service.mapstruct.StructMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements IUserService {

    @Autowired
    private StructMapper structMapper;

    @Override
    public List<SysUserVO> login(String username, String password) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "name", "icon", "properties", "pictures");
        queryWrapper.allEq(SysUser.ValidDataParams);
        // queryWrapper.eq("user_id", dto.getUserId());
        queryWrapper.eq("category_id", dto.getCategoryId());
        queryWrapper.orderByAsc("sort");
        List<SysUser> list = this.list(queryWrapper);
        return null;
    }
}
