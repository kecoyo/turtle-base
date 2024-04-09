package com.kecoyo.turtlebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kecoyo.turtlebase.model.Sms;

public interface SmsService extends IService<Sms> {

    String sendCode(String phone);

    Boolean verifyCode(String phone, String code);
}
