package com.vnzmi.commons.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

@Constraint(validatedBy = { UniqueValidator.class })
public @interface Unique  {
    String value(); //检查的字段
    String message() default "已经存在";
    String table(); //检查的表
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};

    public @interface List {
        Unique[] value();
    }
}
