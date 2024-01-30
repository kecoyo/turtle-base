package com.kecoyo.turtleopen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtleopen.common.config.bean.SecurityProperties;
import com.kecoyo.turtleopen.common.dto.JwtUserDto;
import com.kecoyo.turtleopen.common.security.JwtAuthenticationEntryPoint;
import com.kecoyo.turtleopen.domain.entity.SysUser;
import com.kecoyo.turtleopen.mapper.UserMapper;
import com.kecoyo.turtleopen.service.UserService;
import com.kecoyo.turtleopen.service.mapstruct.StructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {

    @Autowired
    private StructMapper structMapper;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private JwtAuthenticationEntryPoint.TokenProvider tokenProvider;
    @Autowired
    private SecurityProperties properties;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Map<String, Object> login(String username, String password) {
//        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("id", "name", "icon", "properties", "pictures");
//        queryWrapper.allEq(SysUser.ValidDataParams);
        // queryWrapper.eq("user_id", dto.getUserId());
//        queryWrapper.eq("category_id", dto.getCategoryId());
//        queryWrapper.orderByAsc("sort");
//        List<SysUser> list = this.list(queryWrapper);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();

        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};

        return authInfo;
    }

    @Override
    public SysUser getLoginData(String username) {
        return this.getById(1);
    }
}
