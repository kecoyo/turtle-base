package com.kecoyo.turtleopen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtleopen.domain.entity.SysUser;
import com.kecoyo.turtleopen.domain.vo.SysUserVO;

import java.util.List;

public interface IUserService extends IService<SysUser> {

    List<SysUserVO> login(String username, String password);

}
