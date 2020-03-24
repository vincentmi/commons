package com.vnzmi.commons.exception;

public class BusinessException extends RuntimeException {
    protected int code = 600100;
    protected Object data;
    protected String message = "business error";

    public BusinessException() {
    }

    public BusinessException(String msg) {
        this(600100, msg, null);
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

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
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
