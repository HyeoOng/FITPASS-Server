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
public class LoginInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public LoginInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);

        if (session != null) {
            RetUser user = (RetUser) session.getAttribute("user");
            if (user != null) {
                // 로그인한 사용자일 경우 요청을 허용
                return true;
            }
        }

        // 로그인하지 않은 사용자일 경우
        sendJsonResponse(response, "SPL0001");
        return false;
    }

    private void sendJsonResponse(HttpServletResponse response, String code) throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK); // 상태 코드를 200으로 설정

        // 응답 맵 준비
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("code", code);
        responseMap.put("flag", false);

        // 응답을 JSON 형태로 변환하여 출력
        response.getWriter().write(objectMapper.writeValueAsString(responseMap));
    }
}
