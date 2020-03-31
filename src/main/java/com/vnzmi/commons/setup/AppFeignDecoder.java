package com.vnzmi.commons.setup;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vnzmi.commons.exception.BusinessException;
import com.vnzmi.commons.exception.ErrorCode;
import feign.*;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Component
public class AppFeignDecoder implements Decoder {
    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        String bodyText = Util.toString(response.body().asReader(StandardCharsets.UTF_8));

        LoggerFactory.getLogger(getClass()).debug(bodyText);

        LoggerFactory.getLogger(this.getClass()).info(bodyText);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(bodyText);
        JsonNode codeNode = root.get("code");
        JsonNode msgNode = root.get("msg");
        if(codeNode == null || codeNode == null)
        {
            throw new BusinessException(ErrorCode.BAD_RPC_REQUEST,ErrorCode.BUSINESS_ERROR_MESSAGE);
        }
        int code = codeNode.asInt();
        String message = msgNode.asText();

        if(code  != 0 )
        {
            throw new BusinessException(code,message);
        }else{
            JsonNode dataNode = root.get("data");
            return mapper.treeToValue(dataNode, Types.getRawType(type));
        }
    }
}
