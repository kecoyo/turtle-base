package com.kecoyo.turtleopen.common.security;

import com.kecoyo.turtleopen.common.exception.BadRequestException;
import com.kecoyo.turtleopen.domain.entity.SysUser;
import com.kecoyo.turtleopen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserCacheManager userCacheManager;
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserDetails userDetails = userCacheManager.getUserCache(username);
        if (userDetails == null) {
            SysUser user;
            try {
                user = userService.getLoginData(username);
            } catch (EntityNotFoundException e) {
                // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
                throw new UsernameNotFoundException(username, e);
            }
            if (user == null) {
                throw new BadRequestException("账号未激活！");
            }
        }
        return userDetails;
    }

}
