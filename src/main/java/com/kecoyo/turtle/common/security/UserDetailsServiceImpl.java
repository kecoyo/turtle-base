package com.kecoyo.turtle.common.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kecoyo.turtle.model.User;
import com.kecoyo.turtle.service.RoleService;
import com.kecoyo.turtle.service.UserService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserCacheManager userCacheManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUserDto jwtUserDto = userCacheManager.getUserCache(username);
        if (jwtUserDto == null) {
            User user = userService.getByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("用户不存在");
            }

            List<GrantedAuthority> authorities = roleService.list().stream()
                    .map(role -> new AuthorityDto(role.getName())).collect(Collectors.toList());

            jwtUserDto = new JwtUserDto(user, null, authorities);

            // 添加缓存数据
            userCacheManager.addUserCache(username, jwtUserDto);
        }
        return jwtUserDto;
    }

}
