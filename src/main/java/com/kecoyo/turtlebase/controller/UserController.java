package com.kecoyo.turtlebase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kecoyo.turtlebase.model.User;
import com.kecoyo.turtlebase.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "用户服务")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户列表
     *
     * @return
     */
    @RequestMapping("/list")
    public ResponseEntity<List<User>> list() {
        List<User> list = userService.listAll();
        return ResponseEntity.ok(list);
    }

    /**
     * 查找用户，根据用户名
     */
    @RequestMapping("/find")
    public ResponseEntity<User> find(String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

}
