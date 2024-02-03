package com.kecoyo.turtlebase.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtlebase.common.utils.SecurityUtils;
import com.kecoyo.turtlebase.common.utils.ValidateCodeUtils;
import com.kecoyo.turtlebase.domain.entity.Sms;
import com.kecoyo.turtlebase.domain.entity.User;
import com.kecoyo.turtlebase.mapper.SmsMapper;
import com.kecoyo.turtlebase.service.SmsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SmsServiceImpl extends ServiceImpl<SmsMapper, Sms> implements SmsService {

    @Override
    public String sendCode(String phone) {
        Integer userId = SecurityUtils.getCurrentUser2().getId();
        String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
        Sms sms = new Sms();
        sms.setPhone(phone);
        sms.setCode(code);
        sms.setUserId(userId);
        sms.setSendTime(new Date());
        this.save(sms);
        return code;
    }

    @Override
    public Boolean verifyCode(String phone, String code) {
        Integer userId = SecurityUtils.getCurrentUser2().getId();
        QueryWrapper<Sms> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("send_time");
        queryWrapper.last("limit 1");
        Sms sms = this.getOne(queryWrapper);
        if (sms != null) {
            return sms.getCode().equals(code);
        }
        return false;
    }
}
