package com.vnzmi.commons.setup;

import com.vnzmi.commons.exception.BusinessException;
import com.vnzmi.commons.exception.ValidationException;
import com.vnzmi.commons.rest.ApiResponse;
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
import javax.servlet.http.HttpServletRequest;


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
        return ApiResponse.build(e.getCode(), e.getMessage(), e.getData());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse defaultException(HttpServletRequest req, Exception e) {
        logException(e);
        return ApiResponse.build(1, e.getMessage(), null);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ApiResponse entityNotFound(HttpServletRequest req, EntityNotFoundException e) {
        logException(e);
        return ApiResponse.build(600404, "找不到你要操作的数据", null);
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

        logger.error(e.toString(), e);
    }

}
