package com.kecoyo.turtleopen.common.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kecoyo.turtleopen.common.dto.JwtUserDto;
import com.kecoyo.turtleopen.common.exception.BadRequestException;
import com.kecoyo.turtleopen.common.exception.EntityNotFoundException;
import com.kecoyo.turtleopen.domain.dto.UserLoginDto;
import com.kecoyo.turtleopen.service.IUserService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserCacheManager userCacheManager;

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // JwtUserDto jwtUserDto = userCacheManager.getUserCache(username);
        JwtUserDto jwtUserDto = null;
        if (jwtUserDto == null) {
            UserLoginDto user;
            try {
                user = userService.getLoginData(username);
            } catch (EntityNotFoundException e) {
                // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
                throw new UsernameNotFoundException(username, e);
            }
            if (user == null) {
                throw new UsernameNotFoundException("");
            } else {
                if (user.getStatus() == 0) {
                    throw new BadRequestException("账号未激活！");
                }

                jwtUserDto = new JwtUserDto(user, null, new ArrayList<>(), "");

                // 添加缓存数据
                userCacheManager.addUserCache(username, jwtUserDto);
            }
        }
        return jwtUserDto;
    }

}
