package com.baesiru.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class InternalRequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String internal = request.getHeader("X-Internal");
        if (!internal.equals("true")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Internal access only");
            return false;
        }
        return true;
    }
}
