package com.ssafy.fitpass.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.fitpass.user.dto.RetUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class SuperAdminInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public SuperAdminInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session != null) {
            RetUser retUser = (RetUser) session.getAttribute("user");
            if (retUser != null) {
                int adminCode = retUser.getAdmin();
                if (adminCode == 2) { // 최고 관리자 권한 검증
                    return true;
                }
                // 권한이 없는 사용자의 경우
                sendErrorResponse(response, "SPL0002");
                return false;
            }
        }
        // 세션이 없거나 사용자 정보가 없는 경우
        sendErrorResponse(response, "SPL0001");
        return false;
    }

    private void sendErrorResponse(HttpServletResponse response, String code) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("code", code);
        responseMap.put("flag", false);
        response.getWriter().write(objectMapper.writeValueAsString(responseMap));
    }
}
