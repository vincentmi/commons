package com.vnzmi.commons.auth;


public class Auth {

    private static Auth instance;

    private long userId  = -1;

    private long adminId = -1;

    public static Auth getInstance()
    {
        if(instance == null)
        {
            instance = new Auth();
        }
        return instance;
    }

    public long getUserId() {
        return userId;
    }


    /**
     * 检查当前用户是否拥有指定的权限
     */
    public boolean hasPerm(String perm)
    {
        return true;
    }

    public Auth setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getAdminId() {
        return adminId;
    }

    public Auth setAdminId(long adminId) {
        this.adminId = adminId;
        return this;
    }

    public static long userId()
    {
        return getInstance().getUserId();
    }

    public static long adminId()
    {
        return getInstance().getAdminId();
    }

    public static void clear()
    {
        instance = null;
    }
}
