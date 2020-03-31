package com.vnzmi.commons.setup;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Data
@RefreshScope
@Component
public class CommonConfig {

    @Value("${app.authorize.app-id:0}")
    private int authorizeAppId;

    @Value("${app.authorize.name:authorize}")
    private String authorizeName;

    @Value("${app.authorize.url:}")
    private String authorizeUrl;

    @Value("${app.authorize.app-id:0}")
    private int passportAppId;

    @Value("${app.passport.name:passport}")
    private String passportName;

    @Value("${app.passport.url:}")
    private String passportUrl;
}
