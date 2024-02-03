package com.kecoyo.turtlebase.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SmsDto {

    @NotBlank(groups = {Send.class, Verify.class}, message = "手机号不能为空")
    private String phone;

    @NotBlank(groups = {Verify.class}, message = "验证不能为空")
    private String code;


    public @interface Send {
    }

    public @interface Verify {
    }

}
