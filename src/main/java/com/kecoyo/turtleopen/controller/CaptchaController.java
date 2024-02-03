package com.kecoyo.turtleopen.controller;

import com.kecoyo.turtleopen.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/captcha")
@Tag(name = "验证码服务")
public class CaptchaController {

    @Autowired
    private UserService userService;

}
