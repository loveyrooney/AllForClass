package com.chunjae.allforclass.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Logger logger = LoggerFactory.getLogger("LoginInterrupt.class");
        logger.info("PreHandler!!!!!");

        HttpSession session = request.getSession(false);
        if(session==null||session.getAttribute("sessionId")==null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
