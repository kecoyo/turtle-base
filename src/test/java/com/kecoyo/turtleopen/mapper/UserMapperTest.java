package com.kecoyo.turtleopen.mapper;

import com.kecoyo.turtleopen.domain.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * Mapper 测试类
 */
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void queryUserList() {
        Page<User> page = new Page<>(1, 10);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Page<User> list = userMapper.selectPage(page, queryWrapper);
        System.out.println(list.getSize());
        assert list.getSize() > 0;
    }

}
