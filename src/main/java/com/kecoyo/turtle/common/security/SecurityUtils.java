package com.kecoyo.turtle.common.security;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.kecoyo.turtle.common.exception.BadRequestException;

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
    public static JwtUserDto getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        return (JwtUserDto) authentication.getPrincipal();
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
            JwtUserDto userDetails = (JwtUserDto) authentication.getPrincipal();
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
        JwtUserDto userDetails = getCurrentUser();
        return userDetails.getUser().getId();
    }

    /**
     * 获取当前用户的数据权限
     *
     * @return
     */
    public static List<Integer> getCurrentUserDataScope() {
        JwtUserDto userDetails = getCurrentUser();
        return userDetails.getDataScopes();
    }

}
