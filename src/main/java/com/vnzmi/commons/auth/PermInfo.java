package com.vnzmi.commons.auth;

import lombok.Data;

import java.util.List;

@Data
public class PermInfo {
    private long uid  ;
    private String name;
    private String publicName;
    private long applicationId;
    private List<String> perms ;
    private List<String> roles;

}
