package com.vnzmi.commons.exception;

public class AuthorizeException extends BusinessException {
    public AuthorizeException(int code ,String message)
    {
        super(code,message);
    }
}
