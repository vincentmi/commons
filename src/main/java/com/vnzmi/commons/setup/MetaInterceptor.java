package com.vnzmi.commons.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class MetaInterceptor implements HandlerInterceptor {

    @Autowired
    private RequestMeta requestMeta;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        requestMeta.setChannel(getHeader(request,"Channel",""));
        requestMeta.setPhoneType(getHeader(request,"PhoneType",""));
        requestMeta.setPlatform(getHeader(request,"Platform",""));
        requestMeta.setVersion(getHeader(request,"App-Version",""));
        requestMeta.setClientIpAddress(getClientIpAddress(request));
        return true;
    }

    private String getHeader(HttpServletRequest request ,String headerName , String defaultValue)
    {
        String headerValue  = request.getHeader(headerName);
        if(headerValue==null)
        {
            return defaultValue;
        }else{
            return headerValue;
        }
    }

    private String getClientIpAddress(HttpServletRequest request)
    {
        String ip=request.getHeader("X-Original-Forwarded-For");

        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("X-Forwarded-For");
        }

        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("Proxy-Client-IP");
        }

        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getHeader("X-Real-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){
            ip=request.getRemoteAddr();
            if("0:0:0:0:0:0:0:1".equals(ip))
            {
                ip = "127.0.0.1";
            }
        }

        if(ip!=null)
        {
            int pos = ip.indexOf(',');
            if(pos!=-1)
            {
                ip = ip.substring(0,pos);
            }
        }

        return ip;
    }
}
