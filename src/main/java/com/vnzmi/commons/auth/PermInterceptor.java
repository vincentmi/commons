package com.vnzmi.commons.auth;

import com.vnzmi.commons.exception.AuthorizeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PermInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(PermInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Auth.getInstance().setAdminId(new Double(Math.random() * 1000).longValue());
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Perm perm = handlerMethod.getMethodAnnotation(Perm.class);

        if(perm == null)
        {
            return true;
        }else{
            log.info(handler.getClass().toString());
            String[] requiredPerms  = perm.value();
            throw new AuthorizeException(401,"Access deny");
        }

    }



    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        Auth.clear();
    }
}
