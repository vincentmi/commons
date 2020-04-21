package com.vnzmi.commons.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;

public class ValidationException extends BusinessException {

    private String firstMessage;

    public ValidationException()
    {
        setCode(ErrorCode.VALIDATE_FAIL);
        setMessage(ErrorCode.VALIDATE_FAIL_MESSAGE);
        setData(new HashMap<String,String>());
    }

    public String getFirstMessage()
    {
        return firstMessage == null ? "" : firstMessage;
    }

    public ValidationException message(String field , String msg)
    {
        if(firstMessage == null)
        {
            firstMessage = msg;
        }
        HashMap<String,String> data1 = (HashMap<String,String>) data;
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
        ValidationException errException = new ValidationException();
        if(result.hasErrors())
        {
            List<ObjectError> errors = result.getAllErrors();
            List<FieldError> fieldErrorList = result.getFieldErrors();
            ObjectError error ;
            FieldError fieldError ;

            HashMap<String,String>  errorDetail  = new HashMap<String,String>();

            for(int i = 0 ;i<fieldErrorList.size();i++)
            {
                fieldError = fieldErrorList.get(i);
                errException.message(fieldError.getField() , fieldError.getDefaultMessage());
            }
        }
        return errException;

    }
}