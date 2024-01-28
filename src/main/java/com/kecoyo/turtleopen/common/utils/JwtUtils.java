package com.kecoyo.turtleopen.common.utils;

import java.security.Key;
import java.util.Map;

import cn.hutool.core.util.IdUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * JWT工具类
 */
public class JwtUtils {

    public static String generateToken(Map<String, Object> claims, String base64Secret) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        Key signKey = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.builder()
                .signWith(signKey, SignatureAlgorithm.HS512)
                .setId(IdUtil.simpleUUID())
                .claim("user", "admin")
                .setSubject("admin")
                .compact();
    }

    public static Claims parseToken(String token, String base64Secret) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        Key signKey = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.parserBuilder()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
