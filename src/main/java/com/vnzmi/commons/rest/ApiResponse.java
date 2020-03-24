package com.vnzmi.commons.rest;


public class  ApiResponse {
    public static int  SUCCESS = 0;
    public static String SUCCESS_MSG="ok";

    private int code  = 0;
    private String msg = "ok";
    private Object data;


    public ApiResponse(){}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ApiResponse build(Object data)
    {
        ApiResponse response = new ApiResponse();
        response.setCode(SUCCESS);
        response.setMsg(SUCCESS_MSG);
        response.setData(data);
        return response;
    }

    public static ApiResponse build(int code,String msg,Object data)
    {
        ApiResponse response = new ApiResponse();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        return response;

    }
}