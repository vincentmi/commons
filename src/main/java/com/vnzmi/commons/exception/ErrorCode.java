package com.vnzmi.commons.exception;

public  class ErrorCode {
    /**
     * 业务错默认信息
     */
    public final static int  BUSINESS_ERROR = 600100;
    public final static String  BUSINESS_ERROR_MESSAGE = "Business error";

    /**
     * 权限检查失败
     */
    public final static int  ACCESS_DENY = 600403;
    public final static String  ACCESS_DENY_MESSAGE = "权限检查失败.";

    /**
     * 需要登录
     */
    public final static int  LOGIN_REQUIRED = 600401;
    public final static String  LOGIN_REQUIRED_MESSAGE = "需要登录.";

    /**
     * 数据验证失败
     */
    public final static int VALIDATE_FAIL = 600412;
    public final static String VALIDATE_FAIL_MESSAGE = "数据验证失败,请检查.";

    /**
     * 实体未找到
     */
    public static int ENTITY_NOT_FOUND = 600404;
    public static String  ENTITY_NOT_FOUND_MESSAGE = "找不到你要查看的数据.";

    /**
     * RPC 返回数据内容错误
     */
    public static int BAD_RPC_REQUEST = 600400;
    public static String BAD_RPC_REQUEST_MESSAGE = "远程调用失败.";


}
