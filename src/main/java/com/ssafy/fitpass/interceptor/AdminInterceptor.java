package com.ssafy.fitpass.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fitpass.user.RetUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * AdminInterceptor는 특정 요청에 대해 관리자 권한을 가진 사용자만 접근할 수 있도록 하는 역할을 수행하는 인터셉터입니다.
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public AdminInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session != null) {
            RetUser retUser = (RetUser) session.getAttribute("user");
            if (retUser != null) {
                int adminCode = retUser.getAdmin();
                if (adminCode == 1 || adminCode == 2) {
                    return true; // 요청 허용
                }
                // 권한 없는 경우
                sendJsonResponse(response, "접근 권한이 없습니다.");
                return false;
            }
        }

        // 로그인되지 않은 경우
        sendJsonResponse(response, "로그인이 필요합니다.");
        return false;
    }

    private void sendJsonResponse(HttpServletResponse response, String message) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK); // 상태 코드를 항상 200으로 설정

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("msg", message);

        response.getWriter().write(objectMapper.writeValueAsString(responseMap));
    }
}