package com.ssafy.fitpass.interceptor;

import com.ssafy.fitpass.user.RetUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SuperAdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 현재 세션을 가져온다.
        HttpSession session = request.getSession(false);

        if (session != null) { // 세션이 존재하는 경우
            // 세션에서 사용자 정보를 가져온다.
            RetUser retUser = (RetUser) session.getAttribute("user");
            if (retUser != null) { // 사용자 정보가 존재하는 경우
                int adminCode = retUser.getAdmin(); // 사용자 권한 코드(adminCode)를 확인
                if (adminCode == 2) { // 최고 관리자만 접근 가능
                    return true; // 요청 처리를 계속 진행
                }
                // 권한이 없는 사용자의 경우 메시지를 설정하고 요청을 차단
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().write("최고 관리자만 접근 가능합니다.");
                return false;
            }
        }
        // 세션이 없거나 사용자 정보가 없는 경우 메시지를 설정하고 요청을 차단
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("로그인해주세요.");
        return false;
    }
}

