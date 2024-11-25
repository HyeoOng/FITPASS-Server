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
            System.out.println("Session ID: " + session.getId());
            Object userObject = session.getAttribute("user");
            if (userObject instanceof RetUser) {
                RetUser user = (RetUser) userObject;
                System.out.println("logined 상태입니다. User ID: " + user.getUserId());
                return true;
            } else {
                System.out.println("세션이 null입니다");
            }
        }

        System.out.println("logined 상태가 아닙니다.");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        // 메시지를 맵으로 생성
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("msg", "로그인해주세요");

        // 맵을 JSON 문자열로 변환 후 반환
        response.getWriter().write(objectMapper.writeValueAsString(responseMap));
        return false;
    }
}
