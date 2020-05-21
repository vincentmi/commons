package com.vnzmi.commons.setup;

import com.vnzmi.commons.exception.BusinessException;
import com.vnzmi.commons.exception.ErrorCode;
import com.vnzmi.commons.exception.ValidationException;
import com.vnzmi.commons.rest.ApiResponse;
import feign.FeignException;
import feign.codec.DecodeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@ControllerAdvice
public class AppRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ApiResponse businessException(HttpServletRequest req, BusinessException e) {
        logException(e);
        return ApiResponse.build(e.getCode(), e.getMessage(), e.getData());
    }




    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ApiResponse validatingException(HttpServletRequest req, ValidationException e) {
        logException(e);
        return buildValidatingExceptionResponse(e);
    }

    public ApiResponse buildValidatingExceptionResponse(ValidationException e) {
        logException(e);
        return ApiResponse.build(e.getCode(), e.getMessage() + "(" +e.getFirstMessage()+")", e.getData());
    }

    @ExceptionHandler(DecodeException.class)
    @ResponseBody
    public ApiResponse decoderException(HttpServletRequest req, DecodeException e) {
        Throwable src = e.getCause();
        if(src instanceof BusinessException){
            return businessException(req,(BusinessException) src);
        }else{
            return defaultException(req,e);
        }
    }

    @ExceptionHandler(FeignException.class)
    @ResponseBody
    public ApiResponse decoderException(HttpServletRequest req, FeignException e) {

        return ApiResponse.build(e.status(),e.getMessage(),e.contentUTF8());
    }



    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse defaultException(HttpServletRequest req, Exception e) {
        logException(e);
        if(e instanceof  ValidationException){
            //validation exception
            return validatingException(req,(ValidationException) e);
        }else if(e instanceof BusinessException){
            //business exception
            return businessException(req,(BusinessException)e);
        }else {
            // exception
            return ApiResponse.build(1, e.getMessage(), null);
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ApiResponse entityNotFound(HttpServletRequest req, EntityNotFoundException e) {
        logException(e);
        return ApiResponse.build(ErrorCode.ENTITY_NOT_FOUND, ErrorCode.ENTITY_NOT_FOUND_MESSAGE, null);
    }

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseBody
    public ApiResponse entityVersionChanged(HttpServletRequest req , OptimisticLockException e)
    {
        logException(e);
        return ApiResponse.build(ErrorCode.ENTITY_VERSION_CHANGED  ,ErrorCode.ENTITY_NOT_FOUND_MESSAGE,null);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
        }

        logException(ex);

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;

            ApiResponse response = buildValidatingExceptionResponse(

                    ValidationException.buildFromBindingResult(e.getBindingResult())
            );
            return new ResponseEntity(response, headers, status);
        } else {
            ApiResponse response = ApiResponse.build(status.value(), status.getReasonPhrase(), body);
            return new ResponseEntity(response, headers, status);
        }

        //logger.error(ex);

    }


    protected void logException(Exception e) {

        logger.info(e.toString(), e);
    }

}
