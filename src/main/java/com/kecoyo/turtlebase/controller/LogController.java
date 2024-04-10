package com.kecoyo.turtlebase.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/log")
@Tag(name = "日志服务")
public class LogController {

}
