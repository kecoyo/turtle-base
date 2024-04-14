package com.kecoyo.turtlebase.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kecoyo.turtlebase.common.security.dto.JwtUserDto;
import com.kecoyo.turtlebase.domain.User;
import com.kecoyo.turtlebase.service.UserService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserCacheManager userCacheManager;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserDto jwtUserDto = userCacheManager.getUserCache(username);
        if (jwtUserDto == null) {
            User user = userService.getByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("用户不存在");
            }

            jwtUserDto = new JwtUserDto(user, null, null);

            // 添加缓存数据
            userCacheManager.addUserCache(username, jwtUserDto);
        }
        return jwtUserDto;
    }

}
