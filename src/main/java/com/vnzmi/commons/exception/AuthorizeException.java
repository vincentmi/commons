package com.vnzmi.commons.exception;

public class AuthorizeException extends BusinessException {
    public AuthorizeException(int code ,String message)
    {
        super(code,message);
    }

    public static AuthorizeException accessDeny()
    {
        return new AuthorizeException(ErrorCode.ACCESS_DENY,ErrorCode.ACCESS_DENY_MESSAGE);
    }

    public static AuthorizeException loginRequired()
    {
        return new AuthorizeException(ErrorCode.LOGIN_REQUIRED,ErrorCode.LOGIN_REQUIRED_MESSAGE);
    }
}
