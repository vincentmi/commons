package com.vnzmi.commons.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证所选值是否存在与某个表中
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

@Constraint(validatedBy = { ExistValidator.class })
public @interface Exist {
    String value(); //检查的字段
    String message() default "无效的取值";
    String table(); //检查的表
    boolean nullable() default  false ;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};

    public @interface List {
        Unique[] value();
    }
}
