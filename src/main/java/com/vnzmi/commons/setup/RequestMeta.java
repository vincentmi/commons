package com.vnzmi.commons.setup;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class RequestMeta {
    private String phoneType = "";
    private String version = "";
    private String platform="";
    private String channel="";
    private String clientIpAddress = "";
}
