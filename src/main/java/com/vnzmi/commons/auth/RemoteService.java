package com.vnzmi.commons.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vnzmi.commons.setup.CommonConfig;
import com.vnzmi.commons.setup.AppFeignDecoder;
import feign.*;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class RemoteService {

    public interface AuthorizeClient {
        @RequestMapping(
                method = RequestMethod.GET,
                path = "/internal/v1/auth/verify-get",
                produces = "application/json; charset=UTF-8"
        )
        PermInfo verifyAndGet(
                @RequestParam("token") String token,
                @RequestParam("applicationId") long applicationId
        );
    }

    public interface PassportClient {
        @RequestMapping(
                method = RequestMethod.GET ,
                path = "/internal/v1/auth/verify-get",
                produces = "application/json; charset=UTF-8"
        )
        PermInfo verifyAndGet(
                @RequestParam("token") String token,
                @RequestParam("applicationId") long applicationId
        );

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
            HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(new ObjectMapper());
            ObjectFactory<HttpMessageConverters> converter = ()-> new HttpMessageConverters(jsonConverter);
            authorizeClient = Feign.builder()
                    .decoder(appFeignDecoder)
                    .encoder(new SpringEncoder(converter))
                    .contract(new SpringMvcContract())
                    .target(AuthorizeClient.class,commonConfig.getAuthorizeUrl());
        }
        return authorizeClient;
    }

    private PassportClient getPassportClient()
    {
        if(passportClient == null)
        {
            HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(new ObjectMapper());
            ObjectFactory<HttpMessageConverters> converter = ()-> new HttpMessageConverters(jsonConverter);

            passportClient = Feign.builder()
                    .decoder(appFeignDecoder)
                    .encoder(new SpringEncoder(converter))
                    .contract(new SpringMvcContract())
                    .target(PassportClient.class,commonConfig.getPassportUrl());
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
