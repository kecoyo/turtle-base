package com.kecoyo.turtlebase.controller;

import com.kecoyo.turtlebase.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@Tag(name = "日志服务")
public class LogController {

    @Autowired
    private UserService userService;

}
