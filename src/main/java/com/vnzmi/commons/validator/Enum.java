package com.vnzmi.commons.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证指定的值是否在数组中
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

@Constraint(validatedBy = { EnumValidator.class })
public @interface Enum {
    String[] value();
    String message() default "无效的取值,取值应该为{valueSet}";
    boolean nullable() default  false ;
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default {};

    public @interface List {
        Enum[] value();
    }
}
