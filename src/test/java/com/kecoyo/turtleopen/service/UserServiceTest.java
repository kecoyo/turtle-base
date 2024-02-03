package com.kecoyo.turtleopen.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 供应商信息 Mapper 测试类
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void queryAccount() {
        // List<Account> list = accountService.getAllAccountList();
        // System.out.println(list);
        // assert list.size() > 0;
    }

}
