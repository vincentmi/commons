package com.vnzmi.commons.auth;

import com.vnzmi.commons.CommonConfig;
import com.vnzmi.commons.setup.AppFeignConfig;
import com.vnzmi.commons.setup.AppFeignDecoder;
import feign.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class RemoteService {
    public interface AuthorizeClient {
        @RequestLine("GET /api/v1/auth/verify-get?applicationId={applicationId}&token={token}")
        PermInfo verifyAndGet(@Param("token") String token, @Param("applicationId") long applicationId);
    }

    public interface PassportClient {
        @RequestLine("GET /api/v1/auth/verify-get?applicationId={applicationId}&token={applicationId}")
        PermInfo verifyAndGet(@Param("token") String token, @Param("applicationId") long applicationId);

    }

    @Autowired
    private AppFeignDecoder appFeignDecoder;
    @Autowired
    private CommonConfig commonConfig;

    private AuthorizeClient authorizeClient = null;
    private PassportClient passportClient = null;

    private AuthorizeClient getAuthorizeClient()
    {
        if(authorizeClient == null)
        {
            authorizeClient = Feign.builder().decoder(appFeignDecoder)
                    .target(AuthorizeClient.class,commonConfig.getAuthorizeUrl());
        }
        return authorizeClient;
    }

    private PassportClient getPassportClient()
    {
        if(passportClient == null)
        {
            passportClient = Feign.builder().decoder(appFeignDecoder)
                    .target(PassportClient.class,commonConfig.getAuthorizeUrl());
        }
        return passportClient;
    }

    public PermInfo authorizeVerifyAndGet(String token)
    {
        return getAuthorizeClient().verifyAndGet(token,commonConfig.getAuthorizeAppId());
    }

    public PermInfo passportVerifyAndGet(String token)
    {
        return getPassportClient().verifyAndGet(token,commonConfig.getPassportAppId());
    }
}
