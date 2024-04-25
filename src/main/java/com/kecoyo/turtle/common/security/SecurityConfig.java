package com.kecoyo.turtle.common.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import cn.hutool.extra.spring.SpringUtil;

/**
 * SpringSecurity的配置
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // web.ignoring() 通常仅用于提供静态内容
        return (web) -> web.ignoring().requestMatchers("/swagger-ui/**", "/swagger-resources/**",
                "/api-docs/**", "/v3/api-docs/**");
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
        // DaoAuthenticationProvider 从自定义的 userDetailsService.loadUserByUsername
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 方法获取UserDetails
        authProvider.setUserDetailsService(userDetailsService);
        // 设置密码编辑器
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 搜寻匿名标记 url： @AnonymousAccess
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) SpringUtil
                .getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        // 获取匿名标记
        Map<String, String[]> anonymousUrls = getAnonymousUrl(handlerMethodMap);
        String[] patternsPost = anonymousUrls.get(RequestMethodEnum.POST.getType());
        String[] patternsGet = anonymousUrls.get(RequestMethodEnum.GET.getType());
        String[] patternsPut = anonymousUrls.get(RequestMethodEnum.PUT.getType());
        String[] patternsPatch = anonymousUrls.get(RequestMethodEnum.PATCH.getType());
        String[] patternsDelete = anonymousUrls.get(RequestMethodEnum.DELETE.getType());
        String[] patternsAll = anonymousUrls.get(RequestMethodEnum.ALL.getType());

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .logout(logout -> logout.disable())
                .authorizeHttpRequests((authorize) -> {

                    authorize
                            // 自定义匿名访问所有url放行：细腻化到每个 Request 类型
                            // GET
                            .requestMatchers(HttpMethod.GET, patternsGet).permitAll()
                            // POST
                            .requestMatchers(HttpMethod.POST, patternsPost).permitAll()
                            // PUT
                            .requestMatchers(HttpMethod.PUT, patternsPut).permitAll()
                            // PATCH
                            .requestMatchers(HttpMethod.PATCH, patternsPatch).permitAll()
                            // DELETE
                            .requestMatchers(HttpMethod.DELETE, patternsDelete).permitAll()
                            // 所有类型的接口都放行
                            .requestMatchers(patternsAll).permitAll()
                            // 其他接口需要认证
                            .anyRequest().authenticated();
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
    private Map<String, String[]> getAnonymousUrl(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
        Map<String, String[]> anonymousUrls = new HashMap<>(8);
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
                RequestMethodsRequestCondition methodsCondition = infoEntry.getKey().getMethodsCondition();
                List<RequestMethod> requestMethods = new ArrayList<>(methodsCondition.getMethods());
                RequestMethodEnum request = RequestMethodEnum.find(
                        requestMethods.size() == 0 ? RequestMethodEnum.ALL.getType() : requestMethods.get(0).name());

                PathPatternsRequestCondition pathPatternsCondition = infoEntry.getKey().getPathPatternsCondition();
                if (pathPatternsCondition == null) {
                    continue;
                }
                Set<String> pathSet = pathPatternsCondition.getPatterns().stream()
                        .map(PathPattern::getPatternString).collect(HashSet::new, HashSet::add, HashSet::addAll);

                switch (Objects.requireNonNull(request)) {
                    case GET -> get.addAll(pathSet);
                    case POST -> post.addAll(pathSet);
                    case PUT -> put.addAll(pathSet);
                    case PATCH -> patch.addAll(pathSet);
                    case DELETE -> delete.addAll(pathSet);
                    default -> all.addAll(pathSet);
                }
            }
        }
        anonymousUrls.put(RequestMethodEnum.GET.getType(), get.toArray(String[]::new));
        anonymousUrls.put(RequestMethodEnum.POST.getType(), post.toArray(String[]::new));
        anonymousUrls.put(RequestMethodEnum.PUT.getType(), put.toArray(String[]::new));
        anonymousUrls.put(RequestMethodEnum.PATCH.getType(), patch.toArray(String[]::new));
        anonymousUrls.put(RequestMethodEnum.DELETE.getType(), delete.toArray(String[]::new));
        anonymousUrls.put(RequestMethodEnum.ALL.getType(), all.toArray(String[]::new));
        return anonymousUrls;
    }
}
