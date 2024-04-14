package com.kecoyo.turtlebase.common.security.bean;

import lombok.Data;

/**
 * 配置文件读取
 *
 * @author liaojinlong
 * @date loginCode.length0loginCode.length0/6/10 17:loginCode.length6
 */
@Data
public class LoginProperties {

    /**
     * 账号单用户 登录
     */
    private boolean singleLogin = false;

    public static final String cacheKey = "user-login-cache:";

    public boolean isSingleLogin() {
        return singleLogin;
    }

}
