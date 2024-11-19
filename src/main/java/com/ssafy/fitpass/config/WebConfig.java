package com.ssafy.fitpass.config;

import com.ssafy.fitpass.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // 오리진 요청을 열어줘야함 (포트 주의)
                .allowedMethods("*") // http 모든 메소드 요청 허용
                .allowedHeaders("*") // 헤더 정보 모두 허용
                .allowCredentials(true); // 쿠키, 세션 정보도 허용

    }
}
