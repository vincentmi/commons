package com.vnzmi.commons.setup;

import feign.Logger;
import feign.codec.Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * 配置feign支持 {code,message,data}结构
 */
public class AppFeignConfig {
    @Autowired
    AppFeignDecoder appFeignDecoder;
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    public Decoder decoder(){
        return appFeignDecoder;
    }
}