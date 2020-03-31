package com.vnzmi.commons.auth;


import java.util.HashSet;

public class Auth {

    private static Auth instance;

    private long userId = 0;
    private String name = "Guest";
    private String publicName = "Guest";
    private HashSet<String> roles = new HashSet<>();
    private HashSet<String> permissions = new HashSet<>();

    private PermInfo permInfo;

    public static Auth getInstance() {
        if (instance == null) {
            instance = new Auth();
        }
        return instance;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public HashSet<String> getRoles() {
        return roles;
    }


    public HashSet<String> getPermissions() {
        return permissions;
    }


    public long getUserId() {
        return userId;
    }

    public Auth setUserId(long id) {
        this.userId = id;
        return this;
    }

    public Auth setPermInfo(PermInfo permInfo) {
        if (permInfo.getUid() > 0) {
            userId = permInfo.getUid();
        }

        name = permInfo.getName();
        publicName = permInfo.getPublicName();
        if (permInfo.getRoles() != null) {
            roles = new HashSet<>(permInfo.getRoles());
        }
        if (permInfo.getPerms() != null) {
            permissions = new HashSet<>(permInfo.getPerms());
        }
        return this;
    }


    /**
     * 检查当前用户是否拥有指定的权限
     */
    public boolean hasPerm(String perm) {
        return permissions.contains(perm);
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }


    public static long id() {
        return getInstance().getUserId();
    }

    public static String name() {
        return getInstance().getName();
    }

    public static String publicName() {
        return getInstance().getPublicName();
    }

    public static void clear() {
        instance = null;
    }
}
