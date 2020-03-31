package com.vnzmi.commons.setup;

import com.vnzmi.commons.auth.*;
import com.vnzmi.commons.exception.AuthorizeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RemoteService remoteService;

    @Autowired
    private CommonConfig commonConfig;


    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        remoteService = factory.getBean(RemoteService.class);
        commonConfig = factory.getBean(CommonConfig.class);

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String token = getToken(request);
        return checkAuthorize(token,handlerMethod) && checkPassport(token,handlerMethod);

    }

    protected String getToken(HttpServletRequest request)
    {
        String token = null;
        String[] headers = new String[]{"Authorization","Token","authorization"};
        for(int i = 0 ;i<headers.length;i++)
        {
            token = request.getHeader(headers[i]);
            if(token != null)
            {
                break;
            }
        }

        if(token == null)
        {
            token = request.getParameter("token");
        }
        if(token != null)
        {
            if(token.startsWith("Bearer"))
            {
                token = token.substring(6).trim();
            }
        }
        return token;
    }

    protected boolean checkAuthorize(String token, HandlerMethod handlerMethod)
    {
        Authorize annotation = handlerMethod.getMethodAnnotation(Authorize.class);
        if(annotation == null)
        {
            return true;
        }else{
            String[] requiredPerms  = annotation.value();
            boolean silence = annotation.silence();
            PermInfo permInfo = remoteService.authorizeVerifyAndGet(token);
            System.out.println(permInfo.toString());
            Auth.getInstance().setPermInfo(permInfo);
            if(requiredPerms.length == 0){
                if(Auth.id() > 0 )
                {
                    return true;
                }else if(silence) {
                    return true;
                }else{
                    throw  AuthorizeException.loginRequired();
                }
            }else{
                boolean permCheckResult = true;
                for(int i=0;i<requiredPerms.length;i++)
                {
                    if(!Auth.getInstance().hasPerm(requiredPerms[i]))
                    {
                        permCheckResult = false;
                        break;
                    }
                }
                if(permCheckResult){
                    return true;
                }else if(silence) {
                    return true;
                }else{
                    throw AuthorizeException.accessDeny();
                }
            }
        }
    }

    protected boolean checkPassport(String token, HandlerMethod handlerMethod)
    {

        Passport annotation = handlerMethod.getMethodAnnotation(Passport.class);
        if(annotation == null)
        {
            return true;
        }else{
            String[] requiredPerms  = annotation.value();
            boolean silence = annotation.silence();
            PermInfo permInfo = remoteService.passportVerifyAndGet(token);
            Auth.getInstance().setPermInfo(permInfo);
            if(requiredPerms.length == 0){
                if(Auth.id() > 0 )
                {
                    return true;
                }else if(silence) {
                    return true;
                }else{
                    throw AuthorizeException.loginRequired();
                }
            }else{
                boolean permCheckResult = true;
                for(int i=0;i<requiredPerms.length;i++)
                {
                    if(!Auth.getInstance().hasPerm(requiredPerms[i]))
                    {
                        permCheckResult = false;
                        break;
                    }
                }
                if(permCheckResult){
                    return true;
                }else if(silence){
                   return true;
                }else{
                    throw AuthorizeException.accessDeny();
                }
            }
        }
    }



    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        Auth.clear();
    }
}
