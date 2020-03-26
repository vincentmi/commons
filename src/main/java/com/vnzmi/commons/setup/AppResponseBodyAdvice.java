package com.vnzmi.commons.setup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vnzmi.commons.rest.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@ControllerAdvice
public class AppResponseBodyAdvice implements ResponseBodyAdvice {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (aClass == Health.class) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        ApiResponse response;

        if (o == null) {
            return new ApiResponse();
        } else if (o.getClass().equals(Health.class)) {
            return o;
        } else if (o.getClass().equals(ApiResponse.class)) {
            response = (ApiResponse) o;
        } else if (o.getClass().equals(String.class)) {
            //String 特殊处理
            response = ApiResponse.build(o);
            ObjectMapper mapper = new ObjectMapper();
            serverHttpResponse.getHeaders().add("Content-Type","text/json");
            try {
                return mapper.writeValueAsString(response);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            response = ApiResponse.build(o);
        }
        return response;
    }
}
