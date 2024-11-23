package com.ssafy.fitpass.interceptor;

import com.ssafy.fitpass.user.RetUser;
import com.ssafy.fitpass.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

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
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("로그인해주세요.");
        return false;
    }
}

