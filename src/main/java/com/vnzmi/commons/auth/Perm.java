package com.vnzmi.commons.auth;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Perm {
    /**
     * 单个权限
     * @return
     */
    String[] value() default {};
}
