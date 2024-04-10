package com.kecoyo.turtlebase.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/captcha")
@Tag(name = "验证码服务")
public class CaptchaController {

}
