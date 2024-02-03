package com.kecoyo.turtleopen.common.security;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kecoyo.turtleopen.common.DeleteStatus;
import com.kecoyo.turtleopen.common.UserStatus;
import com.kecoyo.turtleopen.common.dto.JwtUserDto;
import com.kecoyo.turtleopen.common.exception.EntityNotFoundException;
import com.kecoyo.turtleopen.domain.entity.User;
import com.kecoyo.turtleopen.service.RoleService;
import com.kecoyo.turtleopen.service.UserService;

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
        // JwtUserDto jwtUserDto = userCacheManager.getUserCache(username);
        JwtUserDto jwtUserDto = null;
        if (jwtUserDto == null) {
            User user;
            try {
                user = userService.findByUsername(username);
            } catch (EntityNotFoundException e) {
                // SpringSecurity会自动转换UsernameNotFoundException为BadCredentialsException
                throw new UsernameNotFoundException(username, e);
            }
            if (user == null) {
                throw new UsernameNotFoundException("");
            } else {
                // if (user.getStatus() == 0) {
                // throw new BadRequestException("账号未激活！");
                // }

                ;

                jwtUserDto = new JwtUserDto();
                BeanUtils.copyProperties(user, jwtUserDto);
                jwtUserDto.setAccountNonLocked(user.getStatus() == UserStatus.ACTIVED.getId()); // 用户帐号已被锁定
                jwtUserDto.setEnabled(user.getDeleted() == DeleteStatus.UNDELETED.getId()); // 用户已失效
                jwtUserDto.setAccountNonExpired(true); // 用户帐号已过期
                jwtUserDto.setCredentialsNonExpired(true); // 用户凭证已过期
                jwtUserDto.setDataScopes(new ArrayList<>());
                jwtUserDto.setAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER"));

                // 添加缓存数据
                userCacheManager.addUserCache(username, jwtUserDto);
            }
        }
        return jwtUserDto;
    }

}
