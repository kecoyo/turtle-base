package com.kecoyo.turtlebase.service.impl;

import com.kecoyo.turtlebase.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kecoyo.turtlebase.common.dto.JwtUserDto;
import com.kecoyo.turtlebase.common.exception.BadRequestException;
import com.kecoyo.turtlebase.common.security.TokenProvider;
import com.kecoyo.turtlebase.common.security.bean.SecurityProperties;
import com.kecoyo.turtlebase.common.utils.HttpClientResult;
import com.kecoyo.turtlebase.common.utils.HttpClientUtils;
import com.kecoyo.turtlebase.domain.entity.App;
import com.kecoyo.turtlebase.domain.entity.User;
import com.kecoyo.turtlebase.domain.entity.UserBind;
import com.kecoyo.turtlebase.mapper.UserMapper;
import com.kecoyo.turtlebase.service.AppService;
import com.kecoyo.turtlebase.service.AuthService;
import com.kecoyo.turtlebase.service.UserBindService;
import com.kecoyo.turtlebase.service.UserService;

@Service
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AppService appService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserBindService userBindService;

    @Override
    public JwtUserDto login(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        String token = tokenProvider.createToken(authentication);
        jwtUserDto.setToken(properties.getTokenStartWith() + token);

        return jwtUserDto;
    }

    @Override
    public JwtUserDto tokenLogin(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token2 = tokenProvider.createToken(authentication);
        JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();

        return jwtUserDto;
    }

    @Override
    public JwtUserDto wxMiniLogin(Integer appId, String code) {
        // 获取应用配置, 默认不存在会抛出异常
        App appInfo = appService.getById(appId, true);

        // 登录凭证校验
        String url = String.format(
            "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
            appInfo.getWechatAppId(),
            appInfo.getWechatAppSecret(),
            code);

        String openid = null;

        try {
            HttpClientResult result = HttpClientUtils.doGet(url);
            JSONObject body = result.getJSONObject();
            Integer errcode = body.getInteger("errcode");
            if (errcode != null) {
                throw new UnsupportedOperationException("微信登录失败: " + body.getString("errmsg"));
            }

            openid = body.getString("openid");
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

        UserBind userBind = userBindService.getByOpenid(appId, openid);
        User user = null;

        // 没有绑定，则创建新用户
        if (userBind == null) {
            // 创建新用户
            user = new User();
            user.setName("微信用户_" + System.currentTimeMillis());
            userService.save(user);

            // 绑定用户
            userBind = new UserBind();
            userBind.setAppId(appId);
            userBind.setOpenid(openid);
            userBind.setUserId(user.getId());
            userBindService.save(userBind);
        } else {
            user = userService.getById(userBind.getUserId());
        }

        String token = tokenProvider.createToken(String.valueOf(user.getId()));
        JwtUserDto jwtUserDto = new JwtUserDto();
        BeanUtils.copyProperties(user, jwtUserDto);
        jwtUserDto.setToken(token);

        return jwtUserDto;
    }

    @Override
    public JwtUserDto wxOauthLogin(Integer appId, String code) {
        throw new UnsupportedOperationException("Unimplemented method 'wxOauthLogin'");
    }

}
