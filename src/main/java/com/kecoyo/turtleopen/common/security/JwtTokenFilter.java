package com.kecoyo.turtleopen.common.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kecoyo.turtleopen.common.security.bean.JwtProperties;
import com.kecoyo.turtleopen.common.utils.JwtUtils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 获取token
        String token = resolveToken(request);
        if (StrUtil.isNotBlank(token)) {
            Object onlineUser = null;
            try {
                String loginKey = loginKey(token);
                onlineUser = getOnlineUser(loginKey);
            } catch (ExpiredJwtException e) {
                log.error(e.getMessage());
            }
            if (onlineUser != null && StringUtils.hasText(token)) {
                String username = getUserName(token);

                // 生成令牌与第三方系统获取令牌方式
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Authentication authentication2 = new UsernamePasswordAuthenticationToken(userDetails,
                        userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication2);
                // Token 续期
                // tokenProvider.checkRenewal(token);
            } else {
                String username = getUserName(token);

                // 生成令牌与第三方系统获取令牌方式
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Authentication authentication2 = new
                UsernamePasswordAuthenticationToken(userDetails,
                userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication2);

                // AnonymousAuthenticationToken authentication2 = new
                // AnonymousAuthenticationToken("anonymous", "anonymous",
                // AuthorityUtils.createAuthorityList("anonymous"));
                // SecurityContextHolder.getContext().setAuthentication(authentication2);

                // Authentication authentication2 = new
                // UsernamePasswordAuthenticationToken("anonymous",
                // "anonymous", AuthorityUtils.createAuthorityList("anonymous"));
                // SecurityContextHolder.getContext().setAuthentication(authentication2);
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 初步检测Token
     *
     * @param request /
     * @return /
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtProperties.getHeader());
        // bearerToken = "Bearer
        // eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxMWJmYWU5M2JmMzU0OTkzYmJjYjAwZGUyMGVkZWIxOSIsInVzZXIiOiJhZG1pbiIsInN1YiI6ImFkbWluIn0.csfVGXtIU2NlGjisDSyGRU8I0_eej2rk04_AwrsX6OZ3VeXyutTM39hVyNCeZYcq-Ipmhuhk_5hXqG_pDNE3Eg";
        if (StringUtils.hasText(bearerToken) &&
                bearerToken.startsWith(jwtProperties.getTokenStartWith())) {
            // 去掉令牌前缀
            return bearerToken.replace(jwtProperties.getTokenStartWith(), "");
        } else {
            log.debug("非法Token：{}", bearerToken);
        }
        return null;
    }

    /**
     * 获取登录用户Redis Key
     *
     * @param token /
     * @return key
     */
    private String loginKey(String token) {
        Claims claims = JwtUtils.parseToken(token, jwtProperties.getBase64Secret());
        String md5Token = DigestUtil.md5Hex(token);
        return jwtProperties.getOnlineKey() + claims.getSubject() + "-" + md5Token;
    }

    /**
     * 普通缓存获取Redis Value
     *
     * @param key 键
     * @return 值
     */
    private Object getOnlineUser(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 依据Token 获取username
     *
     * @param token /
     * @return /
     */
    String getUserName(String token) {
        Claims claims = JwtUtils.parseToken(token, jwtProperties.getBase64Secret());
        return claims.getSubject();
    }
}
