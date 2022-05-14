package com.simple.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class}) // 指定拦截加了该注解的controller

@ResponseBody // 最终通过方法封装返回json数据格式
@Slf4j
public class GlobalErrorHandler {

    // 异常处理方法
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class) // sql相关异常信息
    public R<String> ErrolFun(SQLIntegrityConstraintViolationException exceptionInfo) {
        log.error(exceptionInfo.getMessage());
        // Duplicate entry 'apple' for key 'employee.idx_username'
        if(exceptionInfo.getMessage().contains("Duplicate entry")) {
            String[] arr = exceptionInfo.getMessage().split(" ");
            String msg = "账号" + arr[2] + "已存在";
            return R.error(msg);
        }
        return R.error("失败了呀");
    }
}
