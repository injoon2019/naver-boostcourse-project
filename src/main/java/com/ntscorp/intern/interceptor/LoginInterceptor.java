package com.ntscorp.intern.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object loginInfo = session.getAttribute("reservationEmail");

        if (loginInfo == null) {
            response.sendRedirect("/naverbooking/bookinglogin");
            return false;
        }
        return true;
    }
}
