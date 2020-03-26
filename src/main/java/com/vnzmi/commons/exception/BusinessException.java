package com.vnzmi.commons.exception;

public class BusinessException extends RuntimeException {
    protected int code;
    protected Object data;
    protected String message;

    public BusinessException() {
        this(ErrorCode.BUSINESS_ERROR, ErrorCode.BUSINESS_ERROR_MESSAGE, null);
    }

    public BusinessException(String msg) {
        this(ErrorCode.BUSINESS_ERROR, msg, null);
    }

    public BusinessException(int code, String msg) {
        this(code, msg, null);
    }


    public BusinessException(int code, String msg, Object data) {
        super(msg);
        setMessage(msg);
        setCode(code);
        setData(data);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
