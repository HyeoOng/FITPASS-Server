package com.ssafy.fitpass.config;

import com.ssafy.fitpass.interceptor.AdminInterceptor;
import com.ssafy.fitpass.interceptor.LoginInterceptor;
import com.ssafy.fitpass.interceptor.SuperAdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig는 애플리케이션의 전역 웹 설정을 관리하는 클래스입니다.
 * 인터셉터 및 CORS 설정을 포함하여 Spring MVC의 기본 설정을 확장합니다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final AdminInterceptor adminInterceptor;
    private final SuperAdminInterceptor superAdminInterceptor;

    /**
     * WebConfig 생성자입니다. 인터셉터 객체를 주입받아 초기화합니다.
     *
     * @param loginInterceptor 로그인 관련 요청을 처리하는 인터셉터
     * @param adminInterceptor 관리자 권한 요청을 처리하는 인터셉터
     * @param superAdminInterceptor 최고 관리자 권한 요청을 처리하는 인터셉터
     */
    public WebConfig(LoginInterceptor loginInterceptor, AdminInterceptor adminInterceptor, SuperAdminInterceptor superAdminInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.adminInterceptor = adminInterceptor;
        this.superAdminInterceptor = superAdminInterceptor;
    }

    /**
     * 애플리케이션에 필요한 인터셉터를 등록하고, 특정 경로에 대해 인터셉터가 작동하도록 설정합니다.
     *
     * @param registry 인터셉터를 등록할 수 있는 레지스트리 객체
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 인터셉터 등록
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/*") // 모든 /api/* 경로에 대해 적용
                .addPathPatterns("/api/admin/request/create") // 관리자 요청 경로에 적용
                .excludePathPatterns("/api/users/signup") // 회원가입 요청은 제외
                .excludePathPatterns("/api/users/login") // 로그인 요청은 제외
                .excludePathPatterns("/api/users/logout") // 로그아웃 요청은 제외
                .excludePathPatterns("/api/users/emailCheck") // 이메일 중복 확인 요청은 제외
                .excludePathPatterns("/api/users/nnCheck"); // 닉네임 중복 확인 요청은 제외

        // 관리자 인터셉터 등록
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/admin") // 모든 관리자 목록 볼 수 있음
                .addPathPatterns("/api/admin/request") // 관리자 요청 보는 경로에 적용
                .addPathPatterns("/api/admin/request/delete") // 관리자 요청 지우는 경로에 적용
                .addPathPatterns("/api/sport/**") // /api/sport/ 하위 경로에 대해 적용
                .excludePathPatterns("/api/sport"); // /api/sport 경로는 제외

        // 최고 관리자 인터셉터 등록
        registry.addInterceptor(superAdminInterceptor)
                .addPathPatterns("/api/admin/create") // 관리자 권한 부여
                .addPathPatterns("/api/admin/delete"); // 관리자 권한 삭제
    }

    /**
     * CORS 설정을 추가하여 클라이언트에서의 요청을 허용합니다.
     *
     * @param registry CORS 매핑을 관리하는 레지스트리 객체
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해 CORS 허용
                .allowedOrigins("http://localhost:5173") // 특정 오리진 허용 (프론트엔드 서버 주소)
                .allowedMethods("*") // 모든 HTTP 메서드 허용 (GET, POST, PUT, DELETE 등)
                .allowedHeaders("*") // 모든 요청 헤더 허용
                .allowCredentials(true); // 쿠키 및 세션 정보 허용
    }
}