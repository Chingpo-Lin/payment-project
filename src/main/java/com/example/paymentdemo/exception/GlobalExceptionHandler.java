package com.example.paymentdemo.exception;

import com.example.paymentdemo.utils.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * global exception controller
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // this value can be used to handle specific exception
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData Handler(Exception e) {

        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            return JsonData.buildError(globalException.getCode(), globalException.getMsg());
        } else {
            return JsonData.buildError("global error, unknown error");
        }
    }
}
