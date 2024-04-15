package com.kecoyo.turtlebase.common.security;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.kecoyo.turtlebase.common.exception.BadRequestException;

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
            throw new BadRequestException(HttpStatus.UNAUTHORIZED.value(), "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            LoginUserDetails userDetails = (LoginUserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED.value(), "找不到当前登录的信息");
    }

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    public static Integer getCurrentUserId() {
        LoginUserDetails userDetails = getCurrentUser();
        return userDetails.getUser().getId();
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

}
