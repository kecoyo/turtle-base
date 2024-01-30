package com.kecoyo.turtleopen.common.security;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.kecoyo.turtleopen.common.config.bean.SecurityProperties;
import com.kecoyo.turtleopen.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kecoyo.turtleopen.common.web.ApiError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        ApiError apiError = ApiError.error(HttpStatus.UNAUTHORIZED.value(), "当前登录状态过期");
        Integer code = apiError.getStatus();
        String message = JSON.toJSONString(apiError);

        response.setStatus(code);
        response.getWriter().write(message);
    }

    /**
     * @author /
     */
    @Slf4j
    @Component
    public static class TokenProvider implements InitializingBean {

        private final SecurityProperties properties;
        private final RedisUtils redisUtils;
        public static final String AUTHORITIES_KEY = "user";
        private JwtParser jwtParser;
        private JwtBuilder jwtBuilder;

        public TokenProvider(SecurityProperties properties, RedisUtils redisUtils) {
            this.properties = properties;
            this.redisUtils = redisUtils;
        }

        @Override
        public void afterPropertiesSet() {
            byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
            Key key = Keys.hmacShaKeyFor(keyBytes);
            jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
            jwtBuilder = Jwts.builder().signWith(key, SignatureAlgorithm.HS512);
        }

        /**
         * 创建Token 设置永不过期，
         * Token 的时间有效性转到Redis 维护
         *
         * @param authentication /
         * @return /
         */
        public String createToken(Authentication authentication) {
            return jwtBuilder
                // 加入ID确保生成的 Token 都不一致
                .setId(IdUtil.simpleUUID()).claim(AUTHORITIES_KEY, authentication.getName()).setSubject(authentication.getName()).compact();
        }

        /**
         * 依据Token 获取鉴权信息
         *
         * @param token /
         * @return /
         */
        Authentication getAuthentication(String token) {
            Claims claims = getClaims(token);
            User principal = new User(claims.getSubject(), "******", new ArrayList<>());
            return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
        }

        public Claims getClaims(String token) {
            return jwtParser.parseClaimsJws(token).getBody();
        }

        /**
         * @param token 需要检查的token
         */
        public void checkRenewal(String token) {
            // 判断是否续期token,计算token的过期时间
            String loginKey = loginKey(token);
            long time = redisUtils.getExpire(loginKey) * 1000;
            Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
            // 判断当前时间与过期时间的时间差
            long differ = expireDate.getTime() - System.currentTimeMillis();
            // 如果在续期检查的范围内，则续期
            if (differ <= properties.getDetect()) {
                long renew = time + properties.getRenew();
                redisUtils.expire(loginKey, renew, TimeUnit.MILLISECONDS);
            }
        }

        public String getToken(HttpServletRequest request) {
            final String requestHeader = request.getHeader(properties.getHeader());
            if (requestHeader != null && requestHeader.startsWith(properties.getTokenStartWith())) {
                return requestHeader.substring(7);
            }
            return null;
        }

        /**
         * 获取登录用户RedisKey
         *
         * @param token /
         * @return key
         */
        public String loginKey(String token) {
            Claims claims = getClaims(token);
            String md5Token = DigestUtil.md5Hex(token);
            return properties.getOnlineKey() + claims.getSubject() + "-" + md5Token;
        }
    }
}
