package com.kecoyo.turtlebase.controller;

import com.kecoyo.turtlebase.common.web.ResponseResult;
import com.kecoyo.turtlebase.dto.SmsDto;
import com.kecoyo.turtlebase.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
@Tag(name = "短信服务")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Operation(summary = "发送短信验证码")
    @PostMapping("/sendCode")
    public ResponseResult<String> sendCode(@Validated({SmsDto.Send.class}) @RequestBody SmsDto dto) {
        String result = smsService.sendCode(dto.getPhone());
        return ResponseResult.success(result);
    }

    @Operation(summary = "验证短信验证码")
    @PostMapping("/verifyCode")
    public ResponseResult<Boolean> verifyCode(@Validated({SmsDto.Verify.class}) @RequestBody SmsDto dto) {
        Boolean result = smsService.verifyCode(dto.getPhone(), dto.getCode());
        return ResponseResult.success(result);
    }


}
