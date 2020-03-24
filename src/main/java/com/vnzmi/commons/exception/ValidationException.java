package com.vnzmi.commons.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Hashtable;
import java.util.List;

public class ValidationException extends BusinessException {

    public static int VALIDATE_ERROR_CODE=600400;
    public static String VALIDATE_ERROR_MSG="数据验证失败.请检查数据";


    public ValidationException()
    {
        setCode(VALIDATE_ERROR_CODE);
        setMessage(VALIDATE_ERROR_MSG);
        setData(new Hashtable<String,String>());
    }

    public ValidationException message(String field , String msg)
    {
        Hashtable<String,String> data1 = (Hashtable<String,String>) data;
        String oldMsg  = data1.get(field);
        if(oldMsg == null)
        {
            data1.put(field,msg);
        }else{
            data1.put(field,oldMsg + ","+msg);
        }
        return this;
    }

    public static ValidationException build()
    {
        return new ValidationException();
    }




    public static void  check(BindingResult result) throws ValidationException
    {
        ValidationException err = buildFromBindingResult(result);
        if(err != null)
        {
            throw err;
        }

    }


    public static ValidationException buildFromBindingResult(BindingResult result)
    {
        ValidationException errException = null;
        if(result.hasErrors())
        {
            List<ObjectError> errors = result.getAllErrors();
            List<FieldError> fieldErrorList = result.getFieldErrors();
            ObjectError error ;
            FieldError fieldError ;

            Hashtable<String,String>  errorDetail  = new Hashtable<>();

            for(int i = 0 ;i<fieldErrorList.size();i++)
            {
                fieldError = fieldErrorList.get(i);
                errorDetail.put(fieldError.getField(), fieldError.getDefaultMessage());

            }

            errException = new ValidationException();
            errException.setData(errorDetail);
        }
        return errException;

    }
}