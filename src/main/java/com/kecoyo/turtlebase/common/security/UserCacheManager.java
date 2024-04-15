package com.kecoyo.turtlebase.common.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.kecoyo.turtlebase.common.redis.RedisUtils;

import cn.hutool.core.util.RandomUtil;

/**
 * 用户缓存管理
 **/
@Component
public class UserCacheManager {

    @Value("${login.user-cache.enabled}")
    private Boolean enabled;

    @Value("${login.user-cache.key}")
    private String cacheKey;

    @Value("${login.user-cache.idle-time}")
    private long idleTime;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 返回用户缓存
     *
     * @param userName 用户名
     * @return JwtUserDto
     */
    public LoginUserDetails getUserCache(String userName) {
        if (enabled && StringUtils.isNotEmpty(userName)) {
            // 获取数据
            Object obj = redisUtils.get(cacheKey + userName);
            if (obj != null) {
                return (LoginUserDetails) obj;
            }
        }
        return null;
    }

    /**
     * 添加缓存到Redis
     *
     * @param userName 用户名
     */
    @Async
    public void addUserCache(String userName, LoginUserDetails user) {
        if (StringUtils.isNotEmpty(userName)) {
            // 添加数据, 避免数据同时过期
            long time = idleTime + RandomUtil.randomInt(900, 1800);
            redisUtils.set(cacheKey + userName, user, time);
        }
    }

    /**
     * 清理用户缓存信息
     * 用户信息变更时
     *
     * @param userName 用户名
     */
    @Async
    public void cleanUserCache(String userName) {
        if (StringUtils.isNotEmpty(userName)) {
            // 清除数据
            redisUtils.del(cacheKey + userName);
        }
    }
}
