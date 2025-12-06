package com.github.hitzaki.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SaTokenConfig implements WebMvcConfigurer, StpInterface {

    // Register Sa-Token Interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
            // Specify checking rules
            SaRouter.match("/**")
                    .notMatch("/auth/login", "/contact/submit", "/error") // Exclude public APIs and error page
                    .check(() -> {
                        if ("OPTIONS".equalsIgnoreCase(SaHolder.getRequest().getMethod())) {
                            return;
                        }
                        StpUtil.checkLogin();
                    });
        })).addPathPatterns("/**");
    }

    // CORS Config
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    // StpInterface impl
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // Not using permissions yet, only roles
        return new ArrayList<>();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        // In real app, query DB. For now, assuming admin has 'admin' role.
        // We can store role in token extra or query DB.
        // Let's mock it: if loginId is 1 (admin), give admin role.
        if ("1".equals(loginId.toString())) {
            list.add("admin");
        }
        return list;
    }
}
