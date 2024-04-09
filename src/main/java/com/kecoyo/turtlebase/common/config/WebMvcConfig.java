package com.kecoyo.turtlebase.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 为 RestController 接口配置统一前缀
 */
@Slf4j
@Configuration
// @EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

//    @Value("${spring.controllor.context-path}")
    private String contextPath;

    @Override
    public void configurePathMatch(@NonNull PathMatchConfigurer configurer) {
        if (StringUtils.isNotBlank(contextPath)) {
            configurer.addPathPrefix(contextPath, c -> c.isAnnotationPresent(RestController.class));
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {

            /**
             * 在请求Controller之前被执行
             */
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
                log.info("*******preHandle");
                // String name = request.getParameter("name");
                // if("admin".equals(name)) {
                // return true;
                // } else {
                // return false;
                // }
                return true;
            }

            /**
             * 在请求Controller完成之后被执行
             */
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                    ModelAndView modelAndView) throws Exception {
                log.info("*******postHandle");
            }

            /**
             * 在请求完成后被执行（只有当preHandle的返回值为true时被执行）
             */
            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                    Exception ex) throws Exception {
                log.info("*******afterCompletion");
            }
        };

        registry.addInterceptor(handlerInterceptor); // 添加拦截器
    }

}
