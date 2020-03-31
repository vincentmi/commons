package com.vnzmi.commons.auth;

/**
 * 前台权限验证
 */

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Passport {
    String[] value() default {};
    /**
     * 是否抛出权限检查错误
     * @return
     */
    boolean silence() default false;
}
