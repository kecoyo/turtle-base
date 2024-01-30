package com.kecoyo.turtleopen.common.security;

import java.io.IOException;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.kecoyo.turtleopen.common.annotation.AnonymousAccess;
import com.kecoyo.turtleopen.common.utils.RequestMethodEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kecoyo.turtleopen.common.security.bean.SecurityProperties;
import com.kecoyo.turtleopen.common.utils.RedisUtils;
import com.kecoyo.turtleopen.common.web.ApiError;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

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
                    .setId(IdUtil.simpleUUID()).claim(AUTHORITIES_KEY, authentication.getName())
                    .setSubject(authentication.getName()).compact();
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

    /**
     * SpringSecurity的配置
     */
    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity
    public static class SpringSecurityConfig {

        @Autowired
        private ApplicationContext applicationContext;

        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        @Autowired
        private JwtAccessDeniedHandler jwtAccessDeniedHandler;

        @Autowired
        private UserDetailsService userDetailsService;

        @Bean
        public WebSecurityCustomizer webSecurityCustomizer() {
            // web.ignoring() 通常仅用于提供静态内容
            return (web) -> web.ignoring().requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-resources/**",
                    "/v3/api-docs/**");
        }

        /**
         * 密码加密器
         */
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        /**
         * 调用loadUserByUsername获得UserDetail信息，在AbstractUserDetailsAuthenticationProvider里执行用户状态检查
         */
        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            // DaoAuthenticationProvider 从自定义的 userDetailsService.loadUserByUsername
            // 方法获取UserDetails
            authProvider.setUserDetailsService(userDetailsService);
            // 设置密码编辑器
            authProvider.setPasswordEncoder(passwordEncoder());
            return authProvider;
        }

        @Bean
        public AnonymousAuthenticationFilter anonymousAuthenticationFilter() {
            return new AnonymousAuthenticationFilter("anonymous");
        }

        @Bean
        public AnonymousAuthenticationProvider anonymousAuthenticationProvider() {
            return new AnonymousAuthenticationProvider("anonymous");
        }

        @Bean
        public JwtTokenFilter jwtTokenFilter() {
            return new JwtTokenFilter();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            // 搜寻匿名标记 url： @AnonymousAccess
            RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) applicationContext
                    .getBean("requestMappingHandlerMapping");
            Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
            // 获取匿名标记
            Map<String, Set<String>> anonymousUrls = getAnonymousUrl(handlerMethodMap);
            System.out.println(anonymousUrls);
            http
                    .csrf(csrf -> csrf.disable())
                    .cors(cors -> cors.disable())
                    .httpBasic(httpBasic -> httpBasic.disable())
                    .formLogin(formLogin -> formLogin.disable())
                    .logout(logout -> logout.disable())
                    .authorizeHttpRequests((authorize) -> {
                        authorize.requestMatchers(HttpMethod.GET,
                                anonymousUrls.get(RequestMethodEnum.GET.getType()).toArray(new String[0])).permitAll();
                        authorize.requestMatchers(HttpMethod.POST,
                                anonymousUrls.get(RequestMethodEnum.POST.getType()).toArray(new String[0])).permitAll();
                        authorize.requestMatchers(HttpMethod.PUT,
                                anonymousUrls.get(RequestMethodEnum.PUT.getType()).toArray(new String[0])).permitAll();
                        authorize.requestMatchers(HttpMethod.DELETE,
                                anonymousUrls.get(RequestMethodEnum.DELETE.getType()).toArray(new String[0])).permitAll();
                        authorize.requestMatchers(HttpMethod.PATCH,
                                anonymousUrls.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0])).permitAll();
                        authorize.requestMatchers(
                                anonymousUrls.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0])).permitAll();
                        // authorize.anyRequest().authenticated();
                        authorize.anyRequest().permitAll();
                    })
                    .exceptionHandling((exceptionHandling) -> exceptionHandling
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                            .accessDeniedHandler(jwtAccessDeniedHandler))
                    .userDetailsService(userDetailsService)
                    .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }

        /**
         * 获取匿名标记
         */
        private Map<String, Set<String>> getAnonymousUrl(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
            Map<String, Set<String>> anonymousUrls = new HashMap<>(8);
            Set<String> get = new HashSet<>();
            Set<String> post = new HashSet<>();
            Set<String> put = new HashSet<>();
            Set<String> patch = new HashSet<>();
            Set<String> delete = new HashSet<>();
            Set<String> all = new HashSet<>();
            for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
                HandlerMethod handlerMethod = infoEntry.getValue();
                AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
                if (null != anonymousAccess) {
                    List<RequestMethod> requestMethods = new ArrayList<>(
                            infoEntry.getKey().getMethodsCondition().getMethods());
                    RequestMethodEnum request = RequestMethodEnum.find(
                            requestMethods.size() == 0 ? RequestMethodEnum.ALL.getType() : requestMethods.get(0).name());

                    Set<PathPattern> pathPatternSet = infoEntry.getKey().getPathPatternsCondition().getPatterns();
                    Set<String> pathSet = new HashSet<>();
                    for (PathPattern pp : pathPatternSet) {
                        pathSet.add(pp.getPatternString());
                    }

                    switch (Objects.requireNonNull(request)) {
                        case GET:
                            get.addAll(pathSet);
                            break;
                        case POST:
                            post.addAll(pathSet);
                            break;
                        case PUT:
                            put.addAll(pathSet);
                            break;
                        case PATCH:
                            patch.addAll(pathSet);
                            break;
                        case DELETE:
                            delete.addAll(pathSet);
                            break;
                        default:
                            all.addAll(pathSet);
                            break;
                    }
                }
            }
            anonymousUrls.put(RequestMethodEnum.GET.getType(), get);
            anonymousUrls.put(RequestMethodEnum.POST.getType(), post);
            anonymousUrls.put(RequestMethodEnum.PUT.getType(), put);
            anonymousUrls.put(RequestMethodEnum.PATCH.getType(), patch);
            anonymousUrls.put(RequestMethodEnum.DELETE.getType(), delete);
            anonymousUrls.put(RequestMethodEnum.ALL.getType(), all);
            return anonymousUrls;
        }
    }
}
