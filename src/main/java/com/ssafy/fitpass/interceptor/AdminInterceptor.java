package com.ssafy.fitpass.interceptor;

import com.ssafy.fitpass.user.RetUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * AdminInterceptor는 특정 요청에 대해 관리자 권한을 가진 사용자만 접근할 수 있도록 하는 역할을 수행하는 인터셉터입니다.
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {

    /**
     * 요청 처리 전에 실행되는 메서드로, 사용자의 세션을 확인하고 관리자인지 여부를 검증합니다.
     *
     * @param request  클라이언트의 HTTP 요청 객체
     * @param response 서버의 HTTP 응답 객체
     * @param handler  실행될 핸들러(컨트롤러) 객체
     * @return 요청 처리를 계속 진행할지 여부를 나타내는 boolean 값
     * @throws Exception 처리 중 예외가 발생할 경우 던질 수 있음
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 현재 세션을 가져온다. 세션이 존재하지 않을 경우 null을 반환한다.
        HttpSession session = request.getSession(false);

        if (session != null) { // 세션이 존재하는 경우
            // 세션에서 사용자 정보를 가져온다.
            RetUser retUser = (RetUser) session.getAttribute("user");
            if (retUser != null) { // 사용자 정보가 존재하는 경우
                int adminCode = retUser.getAdmin(); // 사용자 권한 코드(adminCode)를 확인
                if (adminCode == 1 || adminCode == 2) { // 권한 코드가 1(일반 관리자) 또는 2(최고 관리자)인 경우
                    return true; // 요청 처리를 계속 진행
                }
                // 권한이 없는 사용자의 경우 메시지를 설정하고 요청을 차단
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().write("관리자만 접근 가능합니다."); // 한글 메시지 처리
                return false;
            }
        }
        // 세션이 없거나 사용자 정보가 없는 경우 메시지를 설정하고 요청을 차단
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("로그인해주세요."); // 한글 메시지 처리
        return false;
    }
}
