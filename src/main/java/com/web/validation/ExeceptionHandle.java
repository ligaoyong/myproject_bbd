package com.web.validation;

import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExeceptionHandle {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO handle1(MethodArgumentNotValidException e){
        return ResultVO.builder().code("500").message(e.getBindingResult().getFieldError().getDefaultMessage()).build();
    }

    @ExceptionHandler(BindException.class)
    public ResultVO handle2(BindException e){
        return ResultVO.builder().code("500").message(e.getBindingResult().getFieldError().getDefaultMessage()).build();
    }
}
