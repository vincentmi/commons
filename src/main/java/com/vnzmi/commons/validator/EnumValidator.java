package com.vnzmi.commons.validator;

import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;


public class EnumValidator implements javax.validation.ConstraintValidator<Enum, Object> {

    private HashSet<String> valueSet = new HashSet<>();
    private String valueString ;
    private boolean nullable;

    @Override
    public void initialize(Enum constraintAnnotation) {
        String[] values = constraintAnnotation.value();
        StringBuffer sb = new StringBuffer();
        String temp;
        for(int i = 0  ;i<values.length;i++)
        {
            temp = values[i].trim();
            valueSet.add(temp.toLowerCase());
            sb.append(temp);
            sb.append(",");
        }
        if(sb.length() > 0)
        {
            valueString = sb.substring(0,sb.length() -1);
        }
        nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(Object val, ConstraintValidatorContext constraintValidatorContext) {
        if (val == null && this.nullable == false) {
            setMessage(constraintValidatorContext);
            return false;
        }

        boolean result = valueSet.contains(val.toString());

        if(result)
        {
            return true;
        }else{
            setMessage(constraintValidatorContext);
            return false;
        }
    }

    private void setMessage(ConstraintValidatorContext constraintValidatorContext)
    {
        constraintValidatorContext.disableDefaultConstraintViolation();
        String msg = constraintValidatorContext
                .getDefaultConstraintMessageTemplate()
                .replaceAll("\\{values\\}",valueString);

        constraintValidatorContext
                .buildConstraintViolationWithTemplate(msg)
                .addConstraintViolation();
    }
}
