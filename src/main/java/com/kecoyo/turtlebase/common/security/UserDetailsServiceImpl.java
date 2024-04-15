package com.kecoyo.turtlebase.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kecoyo.turtlebase.model.User;
import com.kecoyo.turtlebase.service.UserService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserCacheManager userCacheManager;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserDetails userDetails = userCacheManager.getUserCache(username);
        if (userDetails == null) {
            User user = userService.getByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("用户不存在");
            }

            userDetails = new LoginUserDetails(user, null, null);

            // 添加缓存数据
            userCacheManager.addUserCache(username, userDetails);
        }
        return userDetails;
    }

}
