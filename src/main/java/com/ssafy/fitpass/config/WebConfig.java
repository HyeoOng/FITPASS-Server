package com.ssafy.fitpass.config;

import com.ssafy.fitpass.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/*")
                .excludePathPatterns("/api/users/signup")
                .excludePathPatterns("/api/users/login")
                .excludePathPatterns("/api/users/logout")
                .excludePathPatterns("/api/users/emailCheck")
                .excludePathPatterns("/api/users/nnCheck");
    }
}
