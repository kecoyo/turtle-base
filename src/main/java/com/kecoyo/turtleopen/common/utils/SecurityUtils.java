package com.kecoyo.turtleopen.common.utils;

import java.util.List;

import com.kecoyo.turtleopen.common.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.alibaba.fastjson.JSONObject;
import com.kecoyo.turtleopen.common.security.LoginUserDetails;

import lombok.extern.slf4j.Slf4j;

/**
 * 获取当前登录的用户
 */
@Slf4j
public class SecurityUtils {

    /**
     * 获取当前登录的用户
     *
     * @return LoginUserDetails
     */
    public static LoginUserDetails getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        return (LoginUserDetails) authentication.getPrincipal();
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
    }

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    public static Integer getCurrentUserId() {
        LoginUserDetails userDetails = getCurrentUser();
        Integer userId = userDetails.getUser().getId();
        return userId == 1 ? 1000297 : userId;
    }

    /**
     * 获取当前用户的数据权限
     *
     * @return
     */
    public static List<Integer> getCurrentUserDataScope() {
        LoginUserDetails userDetails = getCurrentUser();
        return userDetails.getDataScopes();
    }

    /**
     * 获取当前用户的平台Token
     *
     * @return
     */
    public static String getCurrentUserPlatformToken() {
        // try {
        // LJToken ljToken = new LJToken();

        // JSONObject jsonObject = new JSONObject();
        // jsonObject.put("userId", getCurrentUserId());
        // jsonObject.put("loginId", 0);
        // jsonObject.put("createTime", System.currentTimeMillis());
        // jsonObject.put("expireTime", 7200000);

        // return ljToken.encrypt(jsonObject);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return null;

        try {
            HttpClientResult result = HttpClientUtils
                    .doPost("https://apix.ljlx.com/Auth/Login?uid=" + getCurrentUserId());
            JSONObject data = result.parseApiData();
            return data.getString("AuthStr");
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

}
