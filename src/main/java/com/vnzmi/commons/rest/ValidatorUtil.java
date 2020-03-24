package com.vnzmi.commons.rest;
import com.vnzmi.commons.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class ValidatorUtil {

    private static Logger logger = LoggerFactory.getLogger(ValidatorUtil.class);

    public static void validate(Object model ,Class<?>... groups) throws ValidationException
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<Object>> constraintViolations = factory.getValidator().validate(model,groups);
        logger.info(Integer.toString(constraintViolations.size()));
        if(constraintViolations.size() > 0 ) {
            Hashtable<String, String> error = new Hashtable<String, String>();
            ConstraintViolation<Object> constraint;

            Iterator<ConstraintViolation<Object>> it = constraintViolations.iterator();
            while (it.hasNext()) {
                constraint = it.next();
                error.put(constraint.getPropertyPath().toString(),constraint.getMessage());
            }

            ValidationException ex = new ValidationException();
            ex.setData(error);
            throw ex;
        }
    }
}
