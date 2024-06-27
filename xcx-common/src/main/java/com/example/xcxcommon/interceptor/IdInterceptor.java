package com.example.xcxcommon.interceptor;

import com.example.xcxcommon.context.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
public class IdInterceptor implements HandlerInterceptor {
    private static Long userId;

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("userId");
        log.info("userId = {}",userId);

        if (userId!= null){
            UserContext.setUser(Long.valueOf(userId));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removerUser();
    }
}
